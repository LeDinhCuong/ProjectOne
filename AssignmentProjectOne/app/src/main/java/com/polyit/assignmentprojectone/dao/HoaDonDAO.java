package com.polyit.assignmentprojectone.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polyit.assignmentprojectone.database.DbHelper;
import com.polyit.assignmentprojectone.model.HoaDon;

import java.util.ArrayList;

public class HoaDonDAO {
    DbHelper dbHelper;
    public HoaDonDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<HoaDon> getDSHoaDon(){
        ArrayList<HoaDon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT hd.mahd,nd.username,nd.hoten,kh.makh,kh.hoten,xe.maxe,xe.tenxe,hd.ngaymua,hd.trangthai,hd.tienmua FROM HOADON hd, KHACHHANG kh,NGUOIDUNG nd,XE Xe WHERE hd.maxe = xe.maxe AND hd.makh = kh.makh AND hd.username = nd.username",null);
        if(cursor.getCount()!= 0){
            cursor.moveToFirst();
            do {
                list.add(new HoaDon(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5),cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9)));
            }while(cursor.moveToNext());
        }

        return list;
    }

    public boolean thayDoiTrangThai(int mahd){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put("trangthai",1);
        long check = sqLiteDatabase.update("HOADON",contentValues,"mahd =? ",new String[]{String.valueOf(mahd)});
        if(check == -1){
            return false;
        }
        return true;
    }

    public boolean addHoaDonDAO(HoaDon hoaDon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngaymua",hoaDon.getNgaymua());
        contentValues.put("tienmua",hoaDon.getTienmua());
        contentValues.put("trangthai",hoaDon.getTrangthai());
        contentValues.put("maxe",hoaDon.getMaxe());
        contentValues.put("makh",hoaDon.getMakh());
        contentValues.put("username",hoaDon.getUsername());

        long check = sqLiteDatabase.insert("HOADON",null,contentValues);
        if(check == -1){
            return false;
        }else
            return true;
    }
    public boolean updateHoaDon(HoaDon hd){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngaymua",hd.getNgaymua());
        contentValues.put("maxe",hd.getMaxe());
        contentValues.put("makh",hd.getMakh());
        long check = sqLiteDatabase.update("HOADON",contentValues,"mahd =?",new String[]{String.valueOf(hd.getMahd())});
        if(check == -1)
            return false;
        return true;
    }

    public Boolean deleteHoaDon(int mahd){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("HOADON","mahd =?",new String[]{String.valueOf(mahd)});
        if(check == -1)
            return false;
        return true;
    }
}

