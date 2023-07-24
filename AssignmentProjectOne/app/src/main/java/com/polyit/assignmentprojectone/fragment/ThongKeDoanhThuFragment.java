package com.polyit.assignmentprojectone.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.polyit.assignmentprojectone.R;
import com.polyit.assignmentprojectone.dao.XeDAO;

import java.util.Calendar;

public class ThongKeDoanhThuFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_thongkedoanhthu,container,false);

    EditText edtStart = view.findViewById(R.id.edtStartDay);
    EditText edtEnd = view.findViewById(R.id.edtEndDay);
    Button btnResult = view.findViewById(R.id.btnResult);
    TextView txtResult = view.findViewById(R.id.txtResult);

    Calendar calendar = Calendar.getInstance();

    edtStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatePickerDialog datePickerDialog =  new DatePickerDialog(
                getContext(),
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

                    edtStart.setText(year + "/" + thang + "/" + ngay) ;
                  }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
      }
    });

    edtEnd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        DatePickerDialog datePickerDialog =  new DatePickerDialog(
                getContext(),
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

                    edtEnd.setText(year + "/" + thang + "/" + ngay) ;
                  }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
      }
    });

    btnResult.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        XeDAO xeDAO =  new XeDAO(getContext());
        String startDay = edtStart.getText().toString();
        String endDay = edtEnd.getText().toString();
        int doanhthu = xeDAO.getDoanhThu(startDay,endDay);
        txtResult.setText(doanhthu + " VND");
      }
    });

    return view;
  }
}
