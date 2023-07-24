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
import com.polyit.assignmentprojectone.dao.KhachHangDAO;
import com.polyit.assignmentprojectone.model.KhachHang;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder>{
  private Context context;
  private ArrayList<KhachHang> list;
  private KhachHangDAO khachHangDAO;

  public KhachHangAdapter(Context context, ArrayList<KhachHang> list,KhachHangDAO khachHangDAO) {
    this.context = context;
    this.list = list;
    this.khachHangDAO = khachHangDAO;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.item_recycler_khachhang,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaKh.setText("ID khách hàng: " + list.get(position).getMakh());
    holder.txtTenKH.setText("Họ tên: " + list.get(position).getHoten());
    holder.txtDiaChi.setText("Địa chỉ: " + list.get(position).getDiachi());

    holder.cvUpdateKH.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLogUpdateInfo(list.get(holder.getAdapterPosition()));
      }
    });
    holder.ivDeleteKH.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int check = khachHangDAO.deleteKhachHang(list.get(holder.getAdapterPosition()).getMakh());
        switch (check){
          case 1:
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            loadData();
            break;
          case 0:
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            break;
          case -1:
            Toast.makeText(context, "Khách hàng đã tồn tại trong hóa đơn, không được phép xóa", Toast.LENGTH_SHORT).show();
            break;
          default:
            break;
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaKh,txtTenKH,txtDiaChi;
    ImageView ivDeleteKH;
    CardView cvUpdateKH;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtMaKh = itemView.findViewById(R.id.txtMaKH);
      txtTenKH = itemView.findViewById(R.id.txtTenKH);
      txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
      ivDeleteKH = itemView.findViewById(R.id.ivDeleteKH);
      cvUpdateKH = itemView.findViewById(R.id.cvUpdateKH);
    }
  }

  private void showDiaLogUpdateInfo(KhachHang khachHang){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
    View view = layoutInflater.inflate(R.layout.dialog_update_khachhang,null);
    builder.setView(view);

    EditText edtMaKH = view.findViewById(R.id.edtMaKH);
    EditText edtHoTen = view.findViewById(R.id.edtHoTen);
    EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
    Button btnUpdate = view.findViewById(R.id.btnUpdate);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    edtMaKH.setText("" + khachHang.getMakh());
    edtHoTen.setText(khachHang.getHoten());
    edtDiaChi.setText(khachHang.getDiachi());

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String hoten = edtHoTen.getText().toString();
        String diachi = edtDiaChi.getText().toString();
        int id = khachHang.getMakh();

        if(hoten.equals("") || diachi.equals("")){
          Toast.makeText(context, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
        }else{
          boolean check = khachHangDAO.updateKhachHang(id,hoten,diachi);
          if(check){
            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          }else{
            Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
          }
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

  private void loadData(){
    list.clear();
    list =  khachHangDAO.getDSKhachHang();
    notifyDataSetChanged();
  }
}
