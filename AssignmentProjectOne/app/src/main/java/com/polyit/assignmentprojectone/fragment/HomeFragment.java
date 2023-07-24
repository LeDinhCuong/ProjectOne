package com.polyit.assignmentprojectone.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.adapter.HangXeAdapter;
import com.polyit.assignmentprojectone.dao.HangXeDao;
import com.polyit.assignmentprojectone.model.HangXe;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ImageView ivHelloScreen = view.findViewById(R.id.ivHelloSceen);
        Glide.with(this).load(R.mipmap.hlloscreen).into(ivHelloScreen);
        return view;
    }

}
