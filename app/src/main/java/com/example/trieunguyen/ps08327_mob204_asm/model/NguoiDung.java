package com.example.trieunguyen.ps08327_mob204_asm.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    //access modifier
    private String username, password, phone, hoTen;

    public NguoiDung() {
    }

    public NguoiDung(String username, String password, String phone, String hoTen) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.hoTen = hoTen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
}
