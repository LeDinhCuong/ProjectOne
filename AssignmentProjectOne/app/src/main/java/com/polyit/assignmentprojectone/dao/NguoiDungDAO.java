package com.polyit.assignmentprojectone.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polyit.assignmentprojectone.database.DbHelper;
import com.polyit.assignmentprojectone.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public NguoiDungDAO(Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN",MODE_PRIVATE);
    }

    public Boolean checkLogin(String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username =? AND password =?", new String[]{user,pass});
        if(cursor.getCount() != 0){
            //save sharedpreference
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",cursor.getString(0));
            editor.putString("password", cursor.getString(1));
            editor.putString("hoten", cursor.getString(2));
            editor.putString("loaitaikhoan", cursor.getString(3));
            editor.commit();
            return true;
        }else
            return false;
    }

    public int updatePass(String user, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE username =? AND password =?", new String[]{user,oldPass});
        if(cursor.getCount() >0 ){
            ContentValues contentValues = new ContentValues();
            contentValues.put("password",newPass);
            long check = sqLiteDatabase.update("NGUOIDUNG",contentValues,"username =?",new String[]{user});
            if(check == -1)
                return -1;
            return 1;
        }
        return 0;
    }

    public ArrayList<NguoiDung> getDSNguoiDung(){
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new NguoiDung(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public Boolean insertNguoiDung(String user, String pass, String hoten, String loaitaikhoan){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",pass);
        contentValues.put("hoten",hoten);
        contentValues.put("loaitaikhoan",loaitaikhoan);
        long check = sqLiteDatabase.insert("NGUOIDUNG",null,contentValues);
        if(check == -1)
            return false;
        else
            return true;
    }

    public Boolean updateNguoiDung(NguoiDung nd){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",nd.getUsername());
        contentValues.put("password",nd.getPassword());
        contentValues.put("hoten",nd.getHoten());
        contentValues.put("loaitaikhoan",nd.getLoaitaikhoan());
        long check = sqLiteDatabase.update("NGUOIDUNG",contentValues,"username =? ",new String[]{nd.getUsername()});
        if(check == -1)
            return false;
        return true;
    }

    public int deleteNguoiDung(String username){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOADON WHERE username =? ",new String[]{username});
        if (cursor.getCount() != 0){
            return -1;
        }else if(username.equals("admin")){
            return -2;
        }

        long check = sqLiteDatabase.delete("NGUOIDUNG","username =?",new String[]{username});
        if(check == -1)
            return 0;
        return 1;
    }
}
