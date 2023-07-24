package com.polyit.assignmentprojectone.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.polyit.assignmentprojectone.dao.HoaDonDAO;
import com.polyit.assignmentprojectone.fragment.QLHoaDonFragment;
import com.polyit.assignmentprojectone.model.HoaDon;
import com.polyit.assignmentprojectone.model.NguoiDung;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private ArrayList<HoaDon> list;
    private Context context;
    private ArrayList<HashMap<String,Object>> listXeHM;
    private ArrayList<HashMap<String,Object>> listKhachHangHM;
    private HoaDonDAO hoaDonDAO;

    public HoaDonAdapter(ArrayList<HoaDon> list, Context context,HoaDonDAO hoaDonDAO,
                         ArrayList<HashMap<String,Object>> listXeHM,
                         ArrayList<HashMap<String,Object>> listKhachHangHM) {
        this.list = list;
        this.context = context;
        this.hoaDonDAO = hoaDonDAO;
        this.listXeHM = listXeHM;
        this.listKhachHangHM = listKhachHangHM;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_hoadon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) { //set data lên textView
        holder.txtMaHD.setText("ID hóa đơn: " + list.get(position).getMahd());
        holder.txtTenNV.setText("Tên nhân viên: " + list.get(position).getTennv());
        holder.txtTenKH.setText("Tên Khách hàng: " + list.get(position).getTenkh());
        holder.txtTenXe.setText("Tên xe: " + list.get(position).getTenxe());
        holder.txtNgayMua.setText("Ngày: " + list.get(position).getNgaymua());
        String trangthai = "";
        if(list.get(position).getTrangthai() == 1){
            trangthai = "Đã thanh toán";
            holder.btnTrangThai.setVisibility(View.GONE);
        }else {
            trangthai = "Chưa thanh toán";
            holder.btnTrangThai.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trạng thái: " + trangthai);
        holder.txtTienMua.setText("Tiền mua: " + list.get(position).getTienmua());
        holder.btnTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                boolean check = hoaDonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMahd());
                if(check){
                    list.clear();
                    list = hoaDonDAO.getDSHoaDon();
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context, "Thanh toán không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.cvUpdateHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLogUpdate(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDeleteHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = hoaDonDAO.deleteHoaDon(list.get(holder.getAdapterPosition()).getMahd());
                if(check){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaHD,txtTenNV,txtTenKH,txtTenXe,txtNgayMua,txtTrangThai,txtTienMua;
        Button btnTrangThai;
        ImageView ivDeleteHD;
        CardView cvUpdateHD;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaHD = itemView.findViewById(R.id.txtMaHD);
            txtTenNV = itemView.findViewById(R.id.txtTenNV);
            txtTenKH = itemView.findViewById(R.id.txtTenKH);
            txtTenXe = itemView.findViewById(R.id.txtTenXe);
            txtNgayMua = itemView.findViewById(R.id.txtNgayMua);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTienMua = itemView.findViewById(R.id.txtTienMua);
            btnTrangThai = itemView.findViewById(R.id.btnTrangThai);
            cvUpdateHD = itemView.findViewById(R.id.cvUpdateHD);
            ivDeleteHD = itemView.findViewById(R.id.ivDeleteHoaDon);
        }
    }
    private void showDiaLogUpdate(HoaDon hd){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_hoadon,null);
        builder.setView(view);

        EditText edtMaHD = view.findViewById(R.id.edtMaHD);
        EditText edtNgay = view.findViewById(R.id.edtNgay);
        Spinner spnKhachHang = view.findViewById(R.id.spnKhachHang);
        Spinner spnXe = view.findViewById(R.id.spnXe);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        edtMaHD.setText("" + hd.getMahd());
        edtNgay.setText(hd.getNgaymua());
        SimpleAdapter simpleAdapterKhachHang = new SimpleAdapter(
                context,
                listKhachHangHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnKhachHang.setAdapter(simpleAdapterKhachHang);

        SimpleAdapter simpleAdapterXe = new SimpleAdapter(
                context,
                listXeHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenxe"},
                new int[]{android.R.id.text1});
        spnXe.setAdapter(simpleAdapterXe);
        //set vị trị cho spnXe
        int indexXe = 0;
        int positionXe = -1;
        for(HashMap<String,Object> item : listXeHM){
            if((int)item.get("maxe") == hd.getMaxe()){
                positionXe = indexXe;
            }
            indexXe ++;
        }
        spnXe.setSelection(positionXe);
        //set vị trị cho spnKhachHang
        int indexKH = 0;
        int positionKH = -1;
        for(HashMap<String,Object> item : listKhachHangHM){
            if((int)item.get("makh") == hd.getMakh()){
                positionKH = indexKH;
            }
            indexKH ++;
        }
        spnKhachHang.setSelection(positionKH);

        Calendar calendar = Calendar.getInstance();
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String ngay = "";
                                String thang = "";
                                if(dayOfMonth < 10){
                                    ngay = "0" + dayOfMonth;
                                }else{
                                    ngay = String.valueOf(dayOfMonth);
                                }

                                if((month+1) < 10){
                                    thang = "0" + (month+1);
                                }else{
                                    thang = String.valueOf(month+1);
                                }

                                edtNgay.setText(year + "/" + thang + "/" + ngay) ;
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hmKH = (HashMap<String, Object>) spnKhachHang.getSelectedItem();
                int makh = (int) hmKH.get("makh");
                HashMap<String,Object> hmXe = (HashMap<String, Object>) spnXe.getSelectedItem();
                int maxe = (int) hmXe.get("maxe");
                int salary = (int) hmXe.get("giaban");
                String ngay = edtNgay.getText().toString();

                HoaDon hoaDon = new HoaDon(hd.getMahd(),hd.getUsername(),hd.getTennv(),makh,hd.getTenkh(),maxe,hd.getTenxe(),ngay,hd.getTrangthai(),salary);
                HoaDonDAO hoaDonDAO = new HoaDonDAO(context);
                boolean check = hoaDonDAO.updateHoaDon(hoaDon);
                if(check){
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    alertDialog.cancel();
                }else
                    Toast.makeText(context, "cập nhật thất bại", Toast.LENGTH_SHORT).show();
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
        list = hoaDonDAO.getDSHoaDon();
        notifyDataSetChanged();
    }
}
