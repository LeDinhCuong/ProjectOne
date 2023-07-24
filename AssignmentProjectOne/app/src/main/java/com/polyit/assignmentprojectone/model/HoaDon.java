package com.polyit.assignmentprojectone.model;

public class HoaDon {
    private int mahd;
    private String username;
    private int makh;
    private int maxe;
    private String ngaymua;
    private int trangthai;
    private int tienmua;
    private String tennv;
    private String tenkh;
    private String tenxe;

    public HoaDon(int mahd, String username,String tennv, int makh,String tenkh, int maxe, String tenxe, String ngaymua, int trangthai, int tienmua) {
        this.mahd = mahd;
        this.username = username;
        this.makh = makh;
        this.maxe = maxe;
        this.ngaymua = ngaymua;
        this.trangthai = trangthai;
        this.tienmua = tienmua;
        this.tennv = tennv;
        this.tenkh = tenkh;
        this.tenxe = tenxe;
    }

    public HoaDon (String username, int makh, int maxe, String ngaymua, int trangthai, int tienmua) {
        this.username = username;
        this.makh = makh;
        this.maxe = maxe;
        this.ngaymua = ngaymua;
        this.trangthai = trangthai;
        this.tienmua = tienmua;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMakh() {
        return makh;
    }

    public void setMakh(int makh) {
        this.makh = makh;
    }

    public int getMaxe() {
        return maxe;
    }

    public void setMaxe(int maxe) {
        this.maxe = maxe;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getTienmua() {
        return tienmua;
    }

    public void setTienmua(int tienmua) {
        this.tienmua = tienmua;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }
}
