package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungDAO {
    private DbHelper dbHelper;

    public static final String TABLE_NAME = "NGUOIDUNG";
    public static final String SQL_NGUOI_DUNG =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "username text primary key," +
                    "password text, " +
                    "phone text, " +
                    "hoTen text" +
                    ");";

    public static final String strInsert1 = "Insert into " + TABLE_NAME +
            " Values('admin', 'admin', '', '')";
    public static final String strInsert2 = "Insert into " + TABLE_NAME +
            " Values('trieu123', '123', '', '')";

    public NguoiDungDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSql = "Select * from NGUOIDUNG Where username = '" + username + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(strSql, null);
        if(cursor.getCount() <= 0) {
            return false;
        } else {
            cursor.moveToFirst();
            String un = cursor.getString(0);
            String pw = cursor.getString(1);
            String phone = cursor.getString(2);
            String hoten = cursor.getString(3);
            NguoiDung nguoiDung = new NguoiDung(un, pw, phone, hoten);
            DbHelper.USER = nguoiDung;
            return true;
        }

    }

    public long insert(NguoiDung nguoiDung) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());
        values.put("phone", nguoiDung.getPhone());
        values.put("hoTen", nguoiDung.getPhone());
        long rowPosition = db.insert(TABLE_NAME, null, values);
        return rowPosition;
    }

    public void update() {

    }

    public void delete() {

    }

    public ArrayList<NguoiDung> getAll() {
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        //đưa trỏ về dòng đầu của ResultSet
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String un = cursor.getString(0);
            String pw = cursor.getString(1);
            String phone = cursor.getString(2);
            String hoTen = cursor.getString(3);
            list.add(new NguoiDung(un, pw, phone, hoTen));
            cursor.moveToNext();
        }
        return null;
    }

    public void getById() {

    }

    public void search() {

    }
}
