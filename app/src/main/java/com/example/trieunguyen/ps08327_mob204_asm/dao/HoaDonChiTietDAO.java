package com.example.trieunguyen.ps08327_mob204_asm.dao;

public class HoaDonChiTietDAO {
    public static final String TABLE_NAME = "HOADONCHITIET";
    public static final String SQL_HOA_DON_CHI_TIET =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maHDCT integer primary key autoincrement," +
                    "maHoaDon text not null, " +
                    "maSach text not null" +
                    "soLuong integer" +
                    ");";

    public void insert() {

    }

    public void update() {

    }

    public void delete() {

    }

    public void getAll() {

    }

    public void getById() {

    }

    public void search() {

    }
}
