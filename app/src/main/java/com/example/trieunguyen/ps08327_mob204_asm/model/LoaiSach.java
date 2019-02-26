package com.example.trieunguyen.ps08327_mob204_asm.model;

import java.io.Serializable;

public class LoaiSach implements Serializable {
    //access modifier
    private String maLoai, ten, mota;
    private int vitri;

    public LoaiSach() {
    }

    public LoaiSach(String maLoai, String ten, String mota, int vitri) {
        this.maLoai = maLoai;
        this.ten = ten;
        this.mota = mota;
        this.vitri = vitri;
    }

    public String getMa() {
        return maLoai;
    }

    public void setMa(String ma) {
        this.maLoai = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getVitri() {
        return vitri;
    }

    public void setVitri(int vitri) {
        this.vitri = vitri;
    }
}
