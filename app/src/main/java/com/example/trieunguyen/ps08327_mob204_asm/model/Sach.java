package com.example.trieunguyen.ps08327_mob204_asm.model;

import java.io.Serializable;

public class Sach implements Serializable {
    //access modifier
    private String maSach, tenLoaiSach, tenSach, tacGia, NXB;
    private int giaBia, soLuong;

    public Sach() {
    }

    public Sach(String maSach, String tenSach, String tacGia, String NXB, int giaBia, int soLuong) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.giaBia = giaBia;
        this.soLuong = soLuong;
    }

    public Sach(String maSach, String tenLoaiSach, String tenSach, String tacGia, String NXB, int giaBia, int soLuong) {
        this.maSach = maSach;
        this.tenLoaiSach = tenLoaiSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.NXB = NXB;
        this.giaBia = giaBia;
        this.soLuong = soLuong;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public int getGiaBia() {
        return giaBia;
    }

    public void setGiaBia(int giaBia) {
        this.giaBia = giaBia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
