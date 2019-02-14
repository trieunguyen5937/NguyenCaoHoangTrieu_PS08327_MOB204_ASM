package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DbHelper dbHelper;

    public static final String TABLE_NAME = "LOAISACH";
    public static final String SQL_LOAI_SACH =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maLoai text primary key, " +
                    "tenLoai text, " +
                    "moTa text, " +
                    "viTri int" +
                    ");";

    public static final String strInsertLS1 = "Insert into " + TABLE_NAME +
            " Values('1', 'Công nghệ thông tin', '', 1)";
    public static final String strInsertLS2 = "Insert into " + TABLE_NAME +
            " Values('2', 'Văn học', '', 2)";


    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    //cach 1
    public long insert(LoaiSach loaiSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLoai", loaiSach.getMa());
        values.put("tenLoai", loaiSach.getTen());
        values.put("moTa", loaiSach.getMota());
        values.put("viTri", loaiSach.getVitri());
        long rowPosition = db.insert(TABLE_NAME, null, values);
        return rowPosition;
    }

    //cach 2
    public long insert(String maLoai, String tenLoai, String moTa, int viTri) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maLoai", maLoai);
        values.put("tenLoai", tenLoai);
        values.put("moTa", moTa);
        values.put("viTri", viTri);
        long rowPosition = db.insert(TABLE_NAME, null, values);
        return rowPosition;
    }

    public void update() {

    }

    public int delete(String maLoai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "maLoai = ?", new String[]{maLoai});
        if (result == 0)
            return -1;
        return 1;
    }

    public ArrayList<LoaiSach> getAll() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        //đưa trỏ về dòng đầu của ResultSet
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String maLoai = cursor.getString(0);
            String tenLoai = cursor.getString(1);
            String moTa = cursor.getString(2);
            int viTri = cursor.getInt(3);
            list.add(new LoaiSach(maLoai, tenLoai, maLoai, viTri));
            cursor.moveToNext();
        }
        return list;
    }

    public void getById() {

    }

    public void search() {

    }
}
