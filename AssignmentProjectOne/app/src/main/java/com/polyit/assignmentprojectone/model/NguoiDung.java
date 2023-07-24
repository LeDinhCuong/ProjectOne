package com.polyit.assignmentprojectone.model;

public class NguoiDung {
  private String username;
  private String hoten;
  private String password;
  private String loaitaikhoan;

  public NguoiDung(String username, String password, String hoten, String loaitaikhoan) {
    this.username = username;
    this.password = password;
    this.hoten = hoten;
    this.loaitaikhoan = loaitaikhoan;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getHoten() {
    return hoten;
  }

  public void setHoten(String hoten) {
    this.hoten = hoten;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLoaitaikhoan() {
    return loaitaikhoan;
  }

  public void setLoaitaikhoan(String loaitaikhoan) {
    this.loaitaikhoan = loaitaikhoan;
  }
}
