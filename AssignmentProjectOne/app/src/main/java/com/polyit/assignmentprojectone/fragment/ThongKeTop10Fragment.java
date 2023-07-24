package com.polyit.assignmentprojectone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.adapter.Top10Adapter;
import com.polyit.assignmentprojectone.dao.XeDAO;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;

public class ThongKeTop10Fragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_thongketop10, container, false);

    RecyclerView recyclerTop10 = view.findViewById(R.id.recyclerTop10);

    XeDAO xeDAO = new XeDAO(getContext());
    ArrayList<Xe> list = xeDAO.getTop10();

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerTop10.setLayoutManager(linearLayoutManager);
    Top10Adapter adapter = new Top10Adapter(getContext(),list);
    recyclerTop10.setAdapter(adapter);

    return view;
  }
}
