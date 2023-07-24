package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.polyit.assignmentprojectone.adapter.XeAdapter;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.dao.XeDAO;
import com.polyit.assignmentprojectone.model.HangXe;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;
import java.util.HashMap;

public class QLXeFragment extends Fragment {
  XeDAO xeDAO;
  ArrayList<Xe> list;
  RecyclerView recyclerQLXe;
  private ArrayList<Xe> originalList;
  private ArrayList<Xe> currentList;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_qlxe,container,false);

    recyclerQLXe = view.findViewById(R.id.recyclerQLXe);
    FloatingActionButton floatAddXe = view.findViewById(R.id.floatAddXe);

    xeDAO = new XeDAO(getContext());

    loadData();

    floatAddXe.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDiaLog();
      }
    });

    EditText edtSearch = view.findViewById(R.id.edtSearchXe);
    ImageView ivSearch = view.findViewById(R.id.ivSearchXe);
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
    list = xeDAO.getDSDauXe();
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerQLXe.setLayoutManager(linearLayoutManager);
    XeAdapter adapter = new XeAdapter(getContext(),list,getDSHangXeHM(),xeDAO);
    recyclerQLXe.setAdapter(adapter);
  }

  private void loadDataSearch(String keyword){
    originalList = xeDAO.getDSDauXe();
    filterData(keyword);

  }

  private void filterData(String keyword){
    currentList = new ArrayList<>();
    for(Xe xe: originalList){
      if(xe.getTenxe().toLowerCase().contains(keyword.toLowerCase())){
        currentList.add(xe);
      }
    }
    XeAdapter adapter = new XeAdapter(getContext(), currentList,getDSHangXeHM(),xeDAO);
    recyclerQLXe.setAdapter(adapter);
  }

  private void showDiaLog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    LayoutInflater inflater = getLayoutInflater();
    View view = inflater.inflate(R.layout.dialog_add_xe,null);
    builder.setView(view);

    EditText edtTenXe = view.findViewById(R.id.edtTenXe);
    EditText edtGiaBan = view.findViewById(R.id.edtGiaBan);
    Spinner spnHangXe = view.findViewById(R.id.spnHangXe);
    Button btnThem = view.findViewById(R.id.btnThem);
    Button btnHuy = view.findViewById(R.id.btnHuy);

    //đưa data từ adapter lên spn
    SimpleAdapter simpleAdapter = new SimpleAdapter(
            getContext(),
            getDSHangXeHM(),
            android.R.layout.simple_list_item_1,
            new String[]{"tenhang"},
            new int[]{android.R.id.text1});
    spnHangXe.setAdapter(simpleAdapter);

    AlertDialog alertDialog = builder.create();
    alertDialog.show();

    btnThem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String tenxe = edtTenXe.getText().toString();
        String giaban = edtGiaBan.getText().toString();
        HashMap<String, Object> hm = (HashMap<String, Object>) spnHangXe.getSelectedItem();
        int mahang = (int) hm.get("mahang");

        if(tenxe.equals("") ||giaban.equals("")){
          Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else if(!TextUtils.isDigitsOnly(giaban)){
          Toast.makeText(getContext(), "Giá tiền phải là số", Toast.LENGTH_SHORT).show();
        }else{
          boolean check = xeDAO.insertXe(tenxe, Integer.parseInt(giaban),mahang);
          if(check){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            loadData();
            alertDialog.cancel();
          }else
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

  private ArrayList<HashMap<String,Object>> getDSHangXeHM(){
    HangXeDao hangXeDao = new HangXeDao(getContext());
    ArrayList<HangXe> list = hangXeDao.getDSHangXe();

    ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

    for(HangXe hangxe : list){
      HashMap<String, Object> hm = new HashMap<>();
      hm.put("mahang",hangxe.getMahang());
      hm.put("tenhang",hangxe.getTenhang());
      listHM.add(hm);
    }
    return listHM;
  }

}
