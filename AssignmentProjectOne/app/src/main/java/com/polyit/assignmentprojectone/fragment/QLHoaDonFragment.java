package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.adapter.HoaDonAdapter;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.dao.HoaDonDAO;
import com.polyit.assignmentprojectone.dao.KhachHangDAO;
import com.polyit.assignmentprojectone.dao.XeDAO;
import com.polyit.assignmentprojectone.model.HangXe;
import com.polyit.assignmentprojectone.model.HoaDon;
import com.polyit.assignmentprojectone.model.KhachHang;
import com.polyit.assignmentprojectone.model.Xe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QLHoaDonFragment extends Fragment {
    HoaDonDAO hoaDonDAO;
    ArrayList<HoaDon> list;
    RecyclerView recyclerViewQLHoaDon;
    private ArrayList<HoaDon> originalList;
    private ArrayList<HoaDon> currentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlhoadon,container,false);

        recyclerViewQLHoaDon = view.findViewById(R.id.recyclerQLHD);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAddHD);

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog();
            }
        });

        EditText edtSearch = view.findViewById(R.id.edtSearchHD);
        ImageView ivSearch = view.findViewById(R.id.ivSearchHD);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = edtSearch.getText().toString().trim();
                loadDataSearch(keyword);
            }
        });

        return view;
    }

    private void loadData(){
        //interface
        //data
        hoaDonDAO = new HoaDonDAO(getContext());
        list = hoaDonDAO.getDSHoaDon();
        //adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewQLHoaDon.setLayoutManager(linearLayoutManager);
        HoaDonAdapter adapter = new HoaDonAdapter(list,getContext(),hoaDonDAO,getDataXeHM(),getDataKhachHangHM());
        recyclerViewQLHoaDon.setAdapter(adapter);
    }

    private void loadDataSearch(String keyword) {
        originalList = hoaDonDAO.getDSHoaDon();
        filterData(keyword);
    }

    private void filterData(String keyword) {
        currentList = new ArrayList<>();
        for(HoaDon hoaDon: originalList){
            if(hoaDon.getTenkh().toLowerCase().contains(keyword.toLowerCase())){
                currentList.add(hoaDon);
            }
        }
        HoaDonAdapter adapter = new HoaDonAdapter(currentList,getContext(),hoaDonDAO,getDataXeHM(),getDataKhachHangHM());
        recyclerViewQLHoaDon.setAdapter(adapter);
    }

    private void showDiaLog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_hoadon,null);
        builder.setView(view);

        Spinner spnKhachHang = view.findViewById(R.id.spnKhachHang);
        Spinner spnXe = view.findViewById(R.id.spnXe);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        SimpleAdapter simpleAdapterXe = new SimpleAdapter(
                getContext(),
                getDataXeHM(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenxe"},
                new int[]{android.R.id.text1});
        spnXe.setAdapter(simpleAdapterXe);

        SimpleAdapter simpleAdapterKhachHang = new SimpleAdapter(
                getContext(),
                getDataKhachHangHM(),
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnKhachHang.setAdapter(simpleAdapterKhachHang);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay ma kh ng dung dang chon
                HashMap<String,Object> hmKH = (HashMap<String, Object>) spnKhachHang.getSelectedItem();
                int makh = (int) hmKH.get("makh");
                //lay ma xe
                HashMap<String,Object> hmXe = (HashMap<String, Object>) spnXe.getSelectedItem();
                int maxe = (int) hmXe.get("maxe");
                //lay tien
                int salary = (int) hmXe.get("giaban");
                addHoaDonFragment(makh,maxe,salary);
                alertDialog.cancel();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }
    private ArrayList<HashMap<String,Object>> getDataKhachHangHM(){
        KhachHangDAO khachHangDAO = new KhachHangDAO(getContext());
        ArrayList<KhachHang> list = khachHangDAO.getDSKhachHang();
        //add data lên spn: spAdt, HMap
        ArrayList<HashMap<String,Object>> listXeHM = new ArrayList<>();
        for(KhachHang kh: list){
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("makh",kh.getMakh());
            hm.put("hoten",kh.getHoten());
            listXeHM.add(hm);
        }
        return listXeHM;
    }
    private ArrayList<HashMap<String,Object>> getDataXeHM(){
        XeDAO xeDAO = new XeDAO(getContext());
        ArrayList<Xe> list = xeDAO.getDSDauXe();

        ArrayList<HashMap<String,Object>> listKhachHangHM = new ArrayList<>();
        for(Xe xe: list){
            HashMap<String,Object> hm = new HashMap<>();
            hm.put("maxe",xe.getMaxe());
            hm.put("tenxe",xe.getTenxe());
            hm.put("giaban",xe.getGiaban());
            listKhachHangHM.add(hm);
        }
        return listKhachHangHM;
    }

    private void addHoaDonFragment(int makh, int maxe, int salary){
        //get user
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("username","");

        //get current date
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = simpleDateFormat.format(currentDate);

        HoaDon hoaDon = new HoaDon(user,makh,maxe,date,0,salary);
        boolean check = hoaDonDAO.addHoaDonDAO(hoaDon);
        if(check){
            Toast.makeText(getContext(), "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        }else{
            Toast.makeText(getContext(), "Thêm hóa đơn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
