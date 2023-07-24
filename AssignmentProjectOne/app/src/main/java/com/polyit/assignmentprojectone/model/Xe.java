package com.polyit.assignmentprojectone.model;

public class Xe {
    private int maxe;
    private String tenxe;
    private int giaban;
    private int mahang;
    private int soluong;
    private String tenhang;

    public Xe(int maxe, String tenxe, int giaban, int mahang) {
        this.maxe = maxe;
        this.tenxe = tenxe;
        this.giaban = giaban;
        this.mahang = mahang;
    }

    public Xe(int maxe, String tenxe, int soluong) {
        this.maxe = maxe;
        this.tenxe = tenxe;
        this.soluong = soluong;
    }

    public Xe(int maxe, String tenxe, int giaban, int mahang, String tenhang) {
        this.maxe = maxe;
        this.tenxe = tenxe;
        this.giaban = giaban;
        this.mahang = mahang;
        this.tenhang = tenhang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getMaxe() {
        return maxe;
    }

    public void setMaxe(int maxe) {
        this.maxe = maxe;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }
}
