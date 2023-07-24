package com.polyit.assignmentprojectone.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder> {
  private Context context;
  private ArrayList<Xe> list;

  public Top10Adapter(Context context, ArrayList<Xe> list) {
    this.context = context;
    this.list = list;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
    View view = inflater.inflate(R.layout.item_recycler_top10,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.txtMaXe.setText("Mã xe: " + list.get(position).getMaxe());
    holder.txtTenXe.setText("Tên xe: " + list.get(position).getTenxe());
    holder.txtSoluong.setText("Số lượng: " + list.get(position).getSoluong());
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView txtMaXe,txtTenXe,txtSoluong;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      txtMaXe = itemView.findViewById(R.id.txtMaXe);
      txtTenXe = itemView.findViewById(R.id.txtTenXe);
      txtSoluong = itemView.findViewById(R.id.txtSoLuong);
    }
  }
}
