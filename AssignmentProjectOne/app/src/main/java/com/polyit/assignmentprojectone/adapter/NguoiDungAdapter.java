package com.polyit.assignmentprojectone.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import com.polyit.assignmentprojectone.dao.NguoiDungDAO;
import com.polyit.assignmentprojectone.model.NguoiDung;

import java.util.ArrayList;
import java.util.HashMap;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {

  private Context context;
  private ArrayList<NguoiDung> list;
  private ArrayList<HashMap<String,Object>> listHM;
  private NguoiDungDAO nguoiDungDAO;

  public NguoiDungAdapter(Context context, ArrayList<NguoiDung> list,ArrayList<HashMap<String,Object>>listHM, NguoiDungDAO nguoiDungDAO) {
    this.context = context;
    this.list = list;
    this.listHM = listHM;
    this.nguoiDungDAO = nguoiDungDAO;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.item_recycler_nguoidung,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtUsername.setText("Username: " + list.get(position).getUsername());
    holder.txtPassword.setText("Password: " + list.get(position).getPassword());
    holder.txtHoTen.setText("Họ tên: " + list.get(position).getHoten());
    holder.txtLoaiTK.setText("Loại tài khoản: " + list.get(position).getLoaitaikhoan());

    holder.cvUpdateNV.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLogUpdateNhanVien(list.get(holder.getAdapterPosition()));
      }
    });
    holder.ivDeleteNV.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int check = nguoiDungDAO.deleteNguoiDung(list.get(holder.getAdapterPosition()).getUsername());
        switch (check){
          case -2:
            Toast.makeText(context, "Không được xóa người dùng này", Toast.LENGTH_SHORT).show();
            break;
          case -1:
            Toast.makeText(context, "Nhân viên đã tồn tại trong hóa đơn, không được phép xóa", Toast.LENGTH_SHORT).show();
            break;
          case 0:
            Toast.makeText(context, "Xóa nhân viên thất bại", Toast.LENGTH_SHORT).show();
            break;
          case 1:
            Toast.makeText(context, "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
            loadData();
            break;
          default: break;
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtUsername, txtPassword, txtHoTen,txtLoaiTK;
    CardView cvUpdateNV;
    ImageView ivDeleteNV;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtUsername = itemView.findViewById(R.id.txtUsername);
      txtPassword = itemView.findViewById(R.id.txtPassword);
      txtHoTen = itemView.findViewById(R.id.txtHoTen);
      txtLoaiTK = itemView.findViewById(R.id.txtLoaiTK);
      cvUpdateNV = itemView.findViewById(R.id.cvUpdateNV);
      ivDeleteNV = itemView.findViewById(R.id.ivDeleteNV);
    }
  }

  private void showDiaLogUpdateNhanVien(NguoiDung nv){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_update_nguoidung,null);
    builder.setView(view);

    EditText edtUser = view.findViewById(R.id.edtUser);
    EditText edtPass = view.findViewById(R.id.edtPass);
    EditText edtHoTen = view.findViewById(R.id.edtHoTen);
    Spinner spnLoaiTK = view.findViewById(R.id.spnLoaiTK);
    Button btnUpdate = view.findViewById(R.id.btnUpdate);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    edtUser.setText(nv.getUsername());
    edtPass.setText(nv.getPassword());
    edtHoTen.setText(nv.getHoten());

    SimpleAdapter simpleAdapter = new SimpleAdapter(
            context,
            listHM,
            android.R.layout.simple_list_item_1,
            new String[]{"loaitaikhoan"},
            new int[]{android.R.id.text1}
    );
    spnLoaiTK.setAdapter(simpleAdapter);

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnUpdate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        String hoten = edtHoTen.getText().toString();
        HashMap<String, Object> hm = (HashMap<String, Object>) spnLoaiTK.getSelectedItem();
        String loaitk = (String) hm.get("loaitaikhoan");

        if(user.equals("") || pass.equals("") || hoten.equals("")){
          Toast.makeText(context, "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
        }else{
          NguoiDung nguoiDung =  new NguoiDung(user,pass,hoten,loaitk);
          boolean check = nguoiDungDAO.updateNguoiDung(nguoiDung);
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
    list = nguoiDungDAO.getDSNguoiDung();
    notifyDataSetChanged();
  }
}
