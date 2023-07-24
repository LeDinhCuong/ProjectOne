package com.polyit.assignmentprojectone.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.dao.XeDAO;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;
import java.util.HashMap;

public class XeAdapter extends RecyclerView.Adapter<XeAdapter.ViewHolder>{
  private Context context;
  private ArrayList<Xe> list;
  private ArrayList<HashMap<String,Object>> listHM;
  private XeDAO xeDAO;

  public XeAdapter(Context context, ArrayList<Xe> list,ArrayList<HashMap<String,Object>>listHM,XeDAO xeDAO) {
    this.context = context;
    this.list = list;
    this.listHM = listHM;
    this.xeDAO = xeDAO;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.item_recycler_xe,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaXe.setText("ID xe: " + list.get(position).getMaxe());
    holder.txtTenXe.setText("Tên xe: " + list.get(position).getTenxe());
    holder.txtTenHang.setText("Hãng xe: " + list.get(position).getTenhang());
    holder.txtGiaBan.setText("Giá bán: " + list.get(position).getGiaban());

    holder.cvUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLogUpdate(list.get(holder.getAdapterPosition()));
      }
    });
    holder.ivDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int check = xeDAO.deleteXe(list.get(holder.getAdapterPosition()).getMaxe());
        switch(check){
          case -1:
            Toast.makeText(context, "Xe đã tồn tại trong hóa đơn, không được phép xóa", Toast.LENGTH_SHORT).show();
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
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaXe,txtTenXe,txtTenHang,txtGiaBan;
    ImageView ivDelete;
    CardView cvUpdate;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtMaXe = itemView.findViewById(R.id.txtMaXe);
      txtTenXe = itemView.findViewById(R.id.txtTenXe);
      txtTenHang = itemView.findViewById(R.id.txtTenHang);
      txtGiaBan = itemView.findViewById(R.id.txtGiaBan);
      ivDelete = itemView.findViewById(R.id.ivDeleteXe);
      cvUpdate = itemView.findViewById(R.id.cvUpdateXe);
    }
  }

  private void showDiaLogUpdate(Xe xe){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_update_xe,null);
    builder.setView(view);

    EditText edtMaXe  = view.findViewById(R.id.edtMaXe);
    EditText edtTenXe = view.findViewById(R.id.edtTenXe);
    EditText edtGiaBan = view.findViewById(R.id.edtGiaBan);
    Spinner spnHangXe = view.findViewById(R.id.spnHangXe);
    Button btnUpdate = view.findViewById(R.id.btnUpdate);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    edtMaXe.setText("" + xe.getMaxe());
    edtTenXe.setText(xe.getTenxe());
    edtGiaBan.setText(String.valueOf(xe.getGiaban()));

    SimpleAdapter simpleAdapter = new SimpleAdapter(
            context,
            listHM,
            android.R.layout.simple_list_item_1,
            new String[]{"tenhang"},
            new int[]{android.R.id.text1}
    );
    spnHangXe.setAdapter(simpleAdapter);

    //set vị trị cho spn
    int index = 0;
    int position = -1;
    for(HashMap<String,Object> item : listHM){
      if((int)item.get("mahang") == xe.getMahang()){
        position = index;
      }
      index ++;
    }
    spnHangXe.setSelection(position);

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String tenxe = edtTenXe.getText().toString();
        String giaban = edtGiaBan.getText().toString();
        HashMap<String, Object> hm = (HashMap<String, Object>) spnHangXe.getSelectedItem();
        int mahang = (int) hm.get("mahang");

        if(tenxe.equals("") || giaban.equals("")){
          Toast.makeText(context, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isDigitsOnly(giaban)) {
          Toast.makeText(context, "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
        }else{
          boolean check = xeDAO.updateXe(xe.getMaxe(),tenxe, Integer.parseInt(giaban),mahang);
          if(check){
            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          }else
            Toast.makeText(context, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
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
    list = xeDAO.getDSDauXe();
    notifyDataSetChanged();
  }
}
