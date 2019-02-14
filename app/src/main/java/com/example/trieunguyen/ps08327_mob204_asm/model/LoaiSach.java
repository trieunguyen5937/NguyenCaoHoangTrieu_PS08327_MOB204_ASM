package com.example.trieunguyen.ps08327_mob204_asm.model;

import java.io.Serializable;

public class LoaiSach implements Serializable {
    //access modifier
    private String ma, ten, mota;
    private int vitri;

    public LoaiSach() {
    }

    public LoaiSach(String ma, String ten, String mota, int vitri) {
        this.ma = ma;
        this.ten = ten;
        this.mota = mota;
        this.vitri = vitri;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
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
