package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.adapter.HangXeAdapter;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.model.HangXe;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QLHangXeFragment extends Fragment {
    HangXeDao hangXeDao;
    ArrayList<HangXe> list;
    RecyclerView recyclerQLHX;
    private ArrayList<HangXe> originalList;
    private ArrayList<HangXe> currentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlhangxe,container,false);

        recyclerQLHX = view.findViewById(R.id.recyclerQLHX);
        FloatingActionButton floatAddQLHX = view.findViewById(R.id.floatAddQLHX);

        //get data
        //set linear layout manager
        //set adapter
        hangXeDao= new HangXeDao(getContext());

        loadData();

        floatAddQLHX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiaLog();
            }
        });

        EditText edtSearch = view.findViewById(R.id.edtSearchHangXe);
        ImageView ivSearch = view.findViewById(R.id.ivSearchHangXe);
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
        list = hangXeDao.getDSHangXe();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerQLHX.setLayoutManager(linearLayoutManager);
        HangXeAdapter adapter = new HangXeAdapter(getContext(),list,hangXeDao);
        recyclerQLHX.setAdapter(adapter);
    }

    public void loadDataSearch(String keyword){
        originalList = hangXeDao.getDSHangXe();
        filterData(keyword);
    }
    public void filterData(String keyword){
        currentList = new ArrayList<>();
        for(HangXe hangXe: originalList) {
            if(hangXe.getTenhang().toLowerCase().contains(keyword.toLowerCase())){
                currentList.add(hangXe);
            }
        }
        HangXeAdapter adapter = new HangXeAdapter(getContext(),currentList,hangXeDao);
        recyclerQLHX.setAdapter(adapter);
    }

    private void showDiaLog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_hangxe,null);
        builder.setView(view);

        EditText edtTenHang = view.findViewById(R.id.edtTenHang);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        AlertDialog alertDialog =  builder.create();
        alertDialog.show();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenhang = edtTenHang.getText().toString();
                if(tenhang.equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = hangXeDao.insertHangXe(tenhang);
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


}
