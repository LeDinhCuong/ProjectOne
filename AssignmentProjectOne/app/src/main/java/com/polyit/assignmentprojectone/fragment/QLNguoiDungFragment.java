package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
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
import com.polyit.assignmentprojectone.adapter.KhachHangAdapter;
import com.polyit.assignmentprojectone.adapter.NguoiDungAdapter;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.dao.NguoiDungDAO;
import com.polyit.assignmentprojectone.model.HangXe;
import com.polyit.assignmentprojectone.model.KhachHang;
import com.polyit.assignmentprojectone.model.NguoiDung;

import java.util.ArrayList;
import java.util.HashMap;

public class QLNguoiDungFragment extends Fragment {
  ArrayList<NguoiDung> list;
  NguoiDungDAO nguoiDungDAO;
  RecyclerView recyclerQlNhanVien;
  private ArrayList<NguoiDung> originalList;
  private ArrayList<NguoiDung> currentList;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_qlnguoidung,container,false);

    recyclerQlNhanVien = view.findViewById(R.id.recyclerQlNV);
    FloatingActionButton floatAdd = view.findViewById(R.id.floatAddNV);

    nguoiDungDAO = new NguoiDungDAO(getContext());
    loadData();

    floatAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLogAdd();
      }
    });

    EditText edtSearch = view.findViewById(R.id.edtSearchTK);
    ImageView ivSearch = view.findViewById(R.id.ivSearchTK);
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
    list = nguoiDungDAO.getDSNguoiDung();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerQlNhanVien.setLayoutManager(linearLayoutManager);
    NguoiDungAdapter adapter = new NguoiDungAdapter(getContext(),list,getDSLoaiTKHM(), nguoiDungDAO);
    recyclerQlNhanVien.setAdapter(adapter);
  }

  private void loadDataSearch(String keyword){
    originalList = nguoiDungDAO.getDSNguoiDung();
    filterData(keyword);

  }
  private void filterData(String keyword){
    currentList = new ArrayList<>();
    for(NguoiDung nguoiDung: originalList){
      if(nguoiDung.getHoten().toLowerCase().contains(keyword.toLowerCase())){
        currentList.add(nguoiDung);
      }
    }
    NguoiDungAdapter adapter = new NguoiDungAdapter(getContext(),currentList,getDSLoaiTKHM(),nguoiDungDAO);
    recyclerQlNhanVien.setAdapter(adapter);
  }

  private void showDiaLogAdd(){
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    LayoutInflater inflater = getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_nguoidung,null);
    builder.setView(view);

    EditText edtUser = view.findViewById(R.id.edtUser);
    EditText edtPass = view.findViewById(R.id.edtPass);
    EditText edtHoTen = view.findViewById(R.id.edtHoTen);
    Spinner spnLoaiTK = view.findViewById(R.id.spnLoaiTK);
    Button btnThem = view.findViewById(R.id.btnThem);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    //đưa data từ adapter lên spn
    SimpleAdapter simpleAdapter = new SimpleAdapter(
            getContext(),
            getDSLoaiTKHM(),
            android.R.layout.simple_list_item_1,
            new String[]{"loaitaikhoan"},
            new int[]{android.R.id.text1});
    spnLoaiTK.setAdapter(simpleAdapter);

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnThem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        String ten = edtHoTen.getText().toString();

        HashMap<String, Object> hm = (HashMap<String, Object>) spnLoaiTK.getSelectedItem();
        String loaitk = (String) hm.get("loaitaikhoan");

        if(user.equals("") || pass.equals("") || ten.equals("")){
          Toast.makeText(getContext(), "Vui lòng không để trống thông tin", Toast.LENGTH_SHORT).show();
        }else{
          boolean check = nguoiDungDAO.insertNguoiDung(user,pass,ten,loaitk);
          if(check){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          }else {
            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
  private ArrayList<HashMap<String,Object>> getDSLoaiTKHM(){
    NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(getContext());
    ArrayList<NguoiDung> list = nguoiDungDAO.getDSNguoiDung();

    ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

    int count = Math.min(list.size(),2);
    for(int i = 0; i < count; i++ ){
      NguoiDung nguoiDung = list.get(i);
      HashMap<String, Object> hm = new HashMap<>();
      hm.put("loaitaikhoan",nguoiDung.getLoaitaikhoan());
      listHM.add(hm);
    }
    return listHM;
  }
}
