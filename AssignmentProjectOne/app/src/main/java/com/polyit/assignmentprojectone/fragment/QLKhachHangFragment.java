package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polyit.assignmentprojectone.MainActivity;
import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.adapter.KhachHangAdapter;
import com.polyit.assignmentprojectone.adapter.XeAdapter;
import com.polyit.assignmentprojectone.dao.KhachHangDAO;
import com.polyit.assignmentprojectone.model.KhachHang;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;

public class QLKhachHangFragment extends Fragment {
  KhachHangDAO khachHangDAO;
  ArrayList<KhachHang> list;
  RecyclerView recyclerQLKhachHang;
  private ArrayList<KhachHang> originalList;
  private ArrayList<KhachHang> currentList;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_qlkhachang, container, false);

    recyclerQLKhachHang = view.findViewById(R.id.recyclerQLKH);
    FloatingActionButton floatAdd = view.findViewById(R.id.floatAddKH);

    khachHangDAO = new KhachHangDAO(getContext());
    loadData();

    floatAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLog();
      }
    });

    EditText edtSearch = view.findViewById(R.id.edtSearchKH);
    ImageView ivSearch = view.findViewById(R.id.ivSearchKH);
    ivSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String keyword = edtSearch.getText().toString().trim();
        loadDataSearch(keyword);
      }
    });

    return view;
  }

  private void loadData() {
    list = khachHangDAO.getDSKhachHang();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerQLKhachHang.setLayoutManager(linearLayoutManager);
    KhachHangAdapter adapter = new KhachHangAdapter(getContext(), list, khachHangDAO);
    recyclerQLKhachHang.setAdapter(adapter);
  }

  private void loadDataSearch(String keyword){
    originalList = khachHangDAO.getDSKhachHang();
    filterData(keyword);

  }
  private void filterData(String keyword){
    currentList = new ArrayList<>();
    for(KhachHang khachHang: originalList){
      if(khachHang.getHoten().toLowerCase().contains(keyword.toLowerCase())){
        currentList.add(khachHang);
      }
    }
    KhachHangAdapter adapter = new KhachHangAdapter(getContext(),currentList,khachHangDAO);
    recyclerQLKhachHang.setAdapter(adapter);
  }

  private void showDiaLog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    LayoutInflater inflater = getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_khachhang, null);
    builder.setView(view);

    EditText edtHoTen = view.findViewById(R.id.edtHoTen);
    EditText edtDiaChi = view.findViewById(R.id.edtDiaChi);
    Button btnThem = view.findViewById(R.id.btnThem);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnThem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String hoten = edtHoTen.getText().toString();
        String diachi = edtDiaChi.getText().toString();

        if (hoten.equals("") || diachi.equals("")) {
          Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
          boolean check = khachHangDAO.insertKhachHang(hoten, diachi);
          if (check) {
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          } else
            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
}
