package com.example.trieunguyen.ps08327_mob204_asm.dao;

public class SachDAO {
    public static final String TABLE_NAME = "SACH";
    public static final String SQL_SACH =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maSach text primary key," +
                    "maTheLoai text, " +
                    "tieuDe text, " +
                    "tacGia text, " +
                    "nxb text, " +
                    "giaBan double, " +
                    "soLuong number" +
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
