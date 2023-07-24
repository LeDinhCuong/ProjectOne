package com.polyit.assignmentprojectone.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.model.HangXe;

import java.util.ArrayList;

public class HangXeAdapter extends RecyclerView.Adapter<HangXeAdapter.ViewHolder>{
  private Context context;
  private ArrayList<HangXe> list;
  private HangXeDao hangXeDao;

  public HangXeAdapter(Context context, ArrayList<HangXe> list,HangXeDao hangXeDao) {
    this.context = context;
    this.list = list;
    this.hangXeDao = hangXeDao;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.item_recycler_hangxe,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaHang.setText("ID hãng xe: " + list.get(position).getMahang());
    holder.txtTenHang.setText("Tên hãng: " + list.get(position).getTenhang());

    holder.ivDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        hangXeDao = new HangXeDao(context);
        int check = hangXeDao.deleteHangXe(list.get(holder.getAdapterPosition()).getMahang());
        switch(check){
          case -1:
            Toast.makeText(context, "Hãng xe đã tồn tại trong hóa đơn hoặc mục khác, không được phép xóa", Toast.LENGTH_SHORT).show();
            break;
          case 0:
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            break;
          case 1:
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            loadData();
            break;
          default:
            break;
        }
      }
    });
    holder.cvUpdateHX.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLogUpdate(list.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaHang, txtTenHang;
    ImageView ivDelete;
    CardView cvUpdateHX;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtMaHang = itemView.findViewById(R.id.txtMaHang);
      txtTenHang = itemView.findViewById(R.id.txtTenHang);
      ivDelete = itemView.findViewById(R.id.ivDeleteHX);
      cvUpdateHX = itemView.findViewById(R.id.cvUpdateHX);
    }
  }

  public void showDiaLogUpdate(HangXe hangXe){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_update_hangxe,null);
    builder.setView(view);

    EditText edtMaHang = view.findViewById(R.id.edtMaHang);
    EditText edtTenHang = view.findViewById(R.id.edtTenHang);
    Button btnUpdate = view.findViewById(R.id.btnUpdate);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    edtMaHang.setText("" + hangXe.getMahang());
    edtTenHang.setText(hangXe.getTenhang());

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int mahang = hangXe.getMahang();
        String tenhang = edtTenHang.getText().toString();

        if(tenhang.equals("")){
          Toast.makeText(context, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
        }else{
          HangXe hangXe = new HangXe(mahang,tenhang);
          Boolean check = hangXeDao.updateHangXe(hangXe);
          if(check){
            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          }else
            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
      }
    });
    btnHuy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        alertDialog.cancel();
      }
    });

  }


  public void loadData(){
    list.clear();
    list = hangXeDao.getDSHangXe();
    notifyDataSetChanged();
  }
}
