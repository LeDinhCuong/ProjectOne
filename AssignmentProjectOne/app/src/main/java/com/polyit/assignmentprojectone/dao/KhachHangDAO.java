package com.polyit.assignmentprojectone.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polyit.assignmentprojectone.database.DbHelper;
import com.polyit.assignmentprojectone.model.KhachHang;

import java.util.ArrayList;

public class KhachHangDAO {
  DbHelper dbHelper;

  public KhachHangDAO(Context context) {
    dbHelper = new DbHelper(context);
  }

  public ArrayList<KhachHang> getDSKhachHang() {
    ArrayList<KhachHang> list = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KHACHHANG", null);
    if (cursor.getCount() != 0) {
      cursor.moveToFirst();
      do {
        list.add(new KhachHang(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
      } while (cursor.moveToNext());
    }
    return list;
  }
  public boolean insertKhachHang(String hoten, String diachi) {
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("hoten", hoten);
    contentValues.put("diachi", diachi);
    long check = sqLiteDatabase.insert("KHACHHANG", null, contentValues);
    if (check == -1)
      return false;
    return true;
  }
  public boolean updateKhachHang(int makh, String hoten, String diachi) {
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("hoten", hoten);
    contentValues.put("diachi", diachi);
    long check = sqLiteDatabase.update("KHACHHANG", contentValues, "makh =? ", new String[]{String.valueOf(makh)});
    if (check == -1)
      return false;
    return true;
  }

  public int deleteKhachHang(int makh){
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOADON WHERE makh =? ",new String[]{String.valueOf(makh)});
    if(cursor.getCount() != 0){
      return -1;
    }
    long check =  sqLiteDatabase.delete("KHACHHANG","makh =?",new String[]{String.valueOf(makh)});
    if(check == -1)
      return 0;
    return 1;
  }
}
