package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungDAO {
    private static final String TAG = "NguoiDungDAO";
    private DbHelper dbHelper;
    private SQLiteDatabase db;

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
        db = dbHelper.getWritableDatabase();
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String strSql = "Select * from NGUOIDUNG Where username = '" + username + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(strSql, null);
        if (cursor.getCount() <= 0) {
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

    public int insert(NguoiDung nguoiDung) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());
        values.put("phone", nguoiDung.getPhone());
        values.put("hoTen", nguoiDung.getPhone());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int update(NguoiDung nguoiDung) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", nguoiDung.getUsername());
        values.put("password", nguoiDung.getPassword());
        values.put("phone", nguoiDung.getPhone());
        values.put("hoTen", nguoiDung.getHoTen());
        int result = db.update(TABLE_NAME, values, "username = ?", new String[]{nguoiDung.getUsername()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public void delete(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "username = ?", new String[]{username});
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
        cursor.close();
        return list;
    }

    public void getById() {

    }

    public void search() {

    }
}
