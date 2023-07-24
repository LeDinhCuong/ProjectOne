package com.polyit.assignmentprojectone.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polyit.assignmentprojectone.database.DbHelper;
import com.polyit.assignmentprojectone.model.Xe;

import java.util.ArrayList;

public class XeDAO {
    DbHelper dbHelper;
    public XeDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    //get all đầu xe trong thư viện
    public ArrayList<Xe> getDSDauXe(){
        ArrayList<Xe> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT xe.maxe,xe.tenxe,xe.giaban,hx.mahang,hx.tenhang FROM XE xe,HANGXE hx WHERE xe.mahang = hx.mahang",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Xe(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4)));
            }while(cursor.moveToNext());
        }
        return list;
    }

    public boolean insertXe(String tenxe, int giaban, int mahang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenxe",tenxe);
        contentValues.put("giaban",giaban);
        contentValues.put("mahang",mahang);
        long check = sqLiteDatabase.insert("XE",null,contentValues);
        if(check == -1)
            return false;
        return true;
    }

    public boolean updateXe(int maxe, String tenxe, int giaban,int mahang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenxe",tenxe);
        contentValues.put("giaban",giaban);
        contentValues.put("mahang",mahang);
        long check = sqLiteDatabase.update("XE",contentValues,"maxe =?",new String[]{String.valueOf(maxe)});
        if(check == -1)
            return false;
        return true;
    }

    public int deleteXe(int maxe){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HOADON WHERE maxe =? ",new String[]{String.valueOf(maxe)});
        if(cursor.getCount() != 0 ){
            return -1;
        }
        long check = sqLiteDatabase.delete("XE","maxe =?" ,new String[]{String.valueOf(maxe)});
        if(check == -1)
            return 0;
        return 1;
    }

    public ArrayList<Xe> getTop10(){
        ArrayList<Xe>list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT hd.maxe, xe.tenxe, COUNT(hd.maxe) FROM HOADON hd, XE xe WHERE hd.maxe = xe.maxe GROUP BY hd.maxe, xe.tenxe ORDER BY COUNT(hd.maxe) DESC LIMIT 10",null);
        if(cursor.getCount() != 0 ){
            cursor.moveToFirst();
            do {
                list.add(new Xe(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while(cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String startdate, String enddate){
        startdate = startdate.replace("/","");
        enddate= enddate.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienmua) FROM HOADON WHERE substr(ngaymua,7) || substr(ngaymua,4,2) || substr(ngaymua,1,2) BETWEEN ? AND ? ", new String[]{startdate,enddate});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
