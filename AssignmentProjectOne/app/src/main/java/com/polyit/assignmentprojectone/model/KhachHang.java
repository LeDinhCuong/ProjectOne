package com.polyit.assignmentprojectone.model;

public class KhachHang {
    private int makh;
    private String hoten;
    private String diachi;

    public KhachHang(int makh, String hoten, String diachi) {
        this.makh = makh;
        this.hoten = hoten;
        this.diachi = diachi;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
}
