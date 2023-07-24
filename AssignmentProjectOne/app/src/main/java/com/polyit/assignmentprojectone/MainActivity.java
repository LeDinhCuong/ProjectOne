package com.polyit.assignmentprojectone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.polyit.assignmentprojectone.dao.NguoiDungDAO;
import com.polyit.assignmentprojectone.fragment.HomeFragment;
import com.polyit.assignmentprojectone.fragment.QLHoaDonFragment;
import com.polyit.assignmentprojectone.fragment.QLHangXeFragment;
import com.polyit.assignmentprojectone.fragment.QLKhachHangFragment;
import com.polyit.assignmentprojectone.fragment.QLNguoiDungFragment;
import com.polyit.assignmentprojectone.fragment.QLXeFragment;
import com.polyit.assignmentprojectone.fragment.ThongKeDoanhThuFragment;
import com.polyit.assignmentprojectone.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        FragmentManager fragment = getSupportFragmentManager();
        fragment.beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =  null;
                switch (item.getItemId()) {
                    case R.id.mQLHoaDon:
                        fragment = new QLHoaDonFragment();
                        break;
                    case R.id.mQLHangXe:
                        fragment = new QLHangXeFragment();
                        break;
                    case R.id.mQLXe:
                        fragment = new QLXeFragment();
                        break;
                    case R.id.mQLKhachHang:
                        fragment = new QLKhachHangFragment();
                        break;
                    case R.id.mNguoiDung:
                        fragment = new QLNguoiDungFragment();
                        break;
                    case R.id.mTop10:
                        fragment = new ThongKeTop10Fragment();
                        break;
                    case R.id.mDoanhThu:
                        fragment = new ThongKeDoanhThuFragment();
                        break;
                    case R.id.mDoiMatKhau:
                        showDiaLogDoiMK();
                        break;
                    case R.id.mThoat:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    default:
                        fragment = new QLHoaDonFragment();
                        break;
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .commit();
                    toolbar.setTitle(item.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        //
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaitaikhoan = sharedPreferences.getString("loaitaikhoan","");
        if(!loaitaikhoan.equals("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
            menu.findItem(R.id.mNguoiDung).setVisible(false);
        }
        String ten = sharedPreferences.getString("hoten","");
        txtTen.setText(ten);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDiaLogDoiMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimk, null);
        builder.setView(view);

        TextView edtOldPass = view.findViewById(R.id.edtOldPass);
        TextView edtNewPass = view.findViewById(R.id.edtNewPass);
        TextView edtReNewPass = view.findViewById(R.id.edtReNewPass);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if(oldPass.equals("") || newPass.equals("") || reNewPass.equals("")) {
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    if (newPass.equals(reNewPass)) {
                        //get user = sharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                        String username = sharedPreferences.getString("username", "");
                        //update
                        NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(MainActivity.this);
                        int check = nguoiDungDAO.updatePass(username, oldPass, newPass);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }else if(check == 0){
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Mập khẩu không trùng với nhau", Toast.LENGTH_SHORT).show();
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