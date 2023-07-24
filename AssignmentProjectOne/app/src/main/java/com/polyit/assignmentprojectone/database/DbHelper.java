package com.polyit.assignmentprojectone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "CUAHANGXEMAY", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbNguoiDung = "CREATE TABLE NGUOIDUNG(" +
                "username text primary key," +
                "password text," +
                "hoten text," +
                "loaitaikhoan text)";
        String dbKhachHang = "CREATE TABLE KHACHHANG(" +
                "makh integer primary key autoincrement," +
                "hoten text," +
                "diachi text)";
        String dbHangxe = "CREATE TABLE HANGXE(" +
                "mahang integer primary key autoincrement," +
                "tenhang text)";
        String dbXe = "CREATE TABLE XE(" +
                "maxe integer primary key autoincrement," +
                "tenxe text," +
                "giaban integer," +
                "mahang integer references HANGXE(mahang))"; //foreign key of HANGXE
        String dbHoaDon= "CREATE TABLE HOADON(" +
                "mahd integer primary key autoincrement," +
                "ngaymua date," +
                "trangthai integer," +
                "tienmua integer," +
                "maxe integer references XE(maxe)," +            //foreign key of XE
                "makh integer references KHACHHANG(makh)," +     //foreign key of KHACHHANG
                "username text references NGUOIDUNG(username))"; //foreign key of NGUOIDUNG
        db.execSQL(dbNguoiDung);
        db.execSQL(dbKhachHang);
        db.execSQL(dbHangxe);
        db.execSQL(dbXe);
        db.execSQL(dbHoaDon);

        db.execSQL("INSERT INTO NGUOIDUNG VALUES" + //username,password,hoten,loaitk
                "('admin','123','Le Dinh Cuong','Admin')," +
                "('nhanvien01','1234','Tran Xuan Loc','Staff')");
        db.execSQL("INSERT INTO KHACHHANG VALUES" + //makh,hoten,diachi
                "(1,'Nguyen Van A','Da Nang')");
        db.execSQL("INSERT INTO HANGXE VALUES" +
                "(1,'Yamaha')");
        db.execSQL("INSERT INTO XE VALUES " + //maxe,tienxe,giaban,mahang
                "(1,'Winner',4000,1)");
        db.execSQL("INSERT INTO HOADON VALUES" + //mahd,ngaymua,trangthai,tienmua,maxe,makh,userna  me
                "(1,'12/10/2022',0,3000,1,1,'admin')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
            db.execSQL("DROP TABLE IF EXISTS KHACHHANG");
            db.execSQL("DROP TABLE IF EXISTS HANGXE");
            db.execSQL("DROP TABLE IF EXISTS XE");
            db.execSQL("DROP TABLE IF EXISTS HOADON");
            onCreate(db);
        }
    }
}
