package com.polyit.assignmentprojectone.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polyit.assignmentprojectone.database.DbHelper;
import com.polyit.assignmentprojectone.model.HangXe;

import java.util.ArrayList;

public class HangXeDao {
  DbHelper dbHelper;
  public HangXeDao(Context context){
    dbHelper = new DbHelper(context);
  }

  public ArrayList<HangXe> getDSHangXe(){
    ArrayList<HangXe> list =  new ArrayList<>();
    SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM HANGXE",null);
    if(cursor.getCount() != 0){
      cursor.moveToFirst();
      do {
        list.add(new HangXe(cursor.getInt(0),cursor.getString(1)));
      }while(cursor.moveToNext());
    }
    return list;
  }

  public Boolean insertHangXe(String tenhang){
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("tenhang",tenhang);
    long check = sqLiteDatabase.insert("HANGXE",null,contentValues);
    if(check == -1)
      return false;
    return true;
  }

  public int deleteHangXe(int id){
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM XE WHERE mahang=? ",new String[]{String.valueOf(id)});
    if(cursor.getCount() != 0){
      return -1;
    }
    long check = sqLiteDatabase.delete("HANGXE","mahang =?",new String[]{String.valueOf(id)});
    if(check == -1)
      return 0;
    return 1;
  }
  public boolean updateHangXe(HangXe hangXe){
    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("tenhang",hangXe.getTenhang());
    long check = sqLiteDatabase.update("HANGXE",contentValues,"mahang =?",new String[]{String.valueOf(hangXe.getMahang())});
    if(check == -1)
      return false;
    return true;
  }

}
