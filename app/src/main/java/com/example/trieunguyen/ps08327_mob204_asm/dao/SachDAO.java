package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private DbHelper dbHelper;
    //    private SQLiteDatabase db;
    public static final String TAG = "SachDAO";
    public static final String TABLE_NAME = "SACH";
    public static final String SQL_SACH =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maSach text primary key," +
                    "tenLoaiSach text, " +
                    "tenSach text, " +
                    "tacGia text, " +
                    "NXB text, " +
                    "giaBia int, " +
                    "soLuong int" +
                    ");";

    public static final String strInsertS1 = "Insert into " + TABLE_NAME +
            " Values('1', 'CNTT', 'Android nâng cao', 'Trieu Nguyen', 'NXB Trẻ', 50000, 5)";
    public static final String strInsertS2 = "Insert into " + TABLE_NAME +
            " Values('2', 'MOBILE', 'Dự án Android', 'Trieu Nguyen', 'NXB FPT', 65000, 10)";
    public static final String strInsertS3 = "Insert into " + TABLE_NAME +
            " Values('3', 'CNTT', 'HTML5 & CSS3', 'Trieu Nguyen', 'NXB FPT', 59000, 10)";
    public static final String strInsertS4 = "Insert into " + TABLE_NAME +
            " Values('4', 'MOBILE', 'Cơ sở dữ liệu', 'Trieu Nguyen', 'NXB FPT', 65000, 10)";
    public static final String strInsertS5 = "Insert into " + TABLE_NAME +
            " Values('5', 'CNTT', 'Android nâng cao', 'Trieu Nguyen', 'NXB Trẻ', 50000, 5)";
    public static final String strInsertS6 = "Insert into " + TABLE_NAME +
            " Values('6', 'MOBILE', 'Tin học văn phòng', 'Trieu Nguyen', 'NXB FPT', 65000, 10)";


    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
//        db = dbHelper.getWritableDatabase();
    }

    public int insert(Sach sach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSach", sach.getMaSach());
        values.put("tenLoaiSach", sach.getTenLoaiSach());
        values.put("tenSach", sach.getTenSach());
        values.put("tacGia", sach.getTacGia());
        values.put("NXB", sach.getNXB());
        values.put("giaBia", sach.getGiaBia());
        values.put("soLuong", sach.getSoLuong());
//        if (checkPrimaryKey(sach.getMaSach())) {
//            int result = db.update(TABLE_NAME, values, "masach=?", new String[]{sach.getMaSach()});
//            if (result == 0) {
//                return -1;
//            }
//        } else {
//            try {
//                if (db.insert(TABLE_NAME, null, values) == -1) {
//                    return -1;
//                }
//            } catch (Exception ex) {
//                Log.e(TAG, ex.toString());
//            }
//        }
//        return 1;
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int update(Sach sach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maSach", sach.getMaSach());
        values.put("tenLoaiSach", sach.getTenLoaiSach());
        values.put("tensach", sach.getTenSach());
        values.put("tacGia", sach.getTacGia());
        values.put("NXB", sach.getNXB());
        values.put("giaBia", sach.getGiaBia());
        values.put("soLuong", sach.getSoLuong());
        int result = db.update(TABLE_NAME, values, "maSach=?", new
                String[]{sach.getMaSach()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int delete(String maSach) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete(TABLE_NAME, "maSach=?", new String[]{maSach});
        if (result == 0)
            return -1;
        return 1;
    }

    public ArrayList<Sach> getAll() {
        ArrayList<Sach> list = new ArrayList<Sach>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String masach = cursor.getString(0);
                String tenloaisach = cursor.getString(1);
                String tensach = cursor.getString(2);
                String tacgia = cursor.getString(3);
                String nxb = cursor.getString(4);
                int giabia = Integer.parseInt(cursor.getString(5));
                int soluong = Integer.parseInt(cursor.getString(6));
                Sach sach = new Sach(masach, tenloaisach, tensach, tacgia, nxb, giabia, soluong);
                list.add(sach);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    //check
    public boolean checkPrimaryKey(String strPrimaryKey) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //SELECT
        String[] columns = {"maSach"};
        //WHERE clause
        String selection = "maSach=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null,
                    null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //check
    public Sach checkBook(String strPrimaryKey) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Sach s = new Sach();
        //SELECT
        String[] columns = {"maSach"};
        //WHERE clause
        String selection = "maSach=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            while (c.isAfterLast() == false) {
                s.setMaSach(c.getString(0));
                s.setTenLoaiSach(c.getString(1));
                s.setTenSach(c.getString(2));
                s.setTacGia(c.getString(3));
                s.setNXB(c.getString(4));
                s.setGiaBia(c.getInt(5));
                s.setSoLuong(c.getInt(6));
                Log.d("//=====", s.toString());
                break;
            }
            c.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //search
    public Sach getSachById(String maSach) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Sach s = null;
        //WHERE clause
        String selection = "maSach=?";
        //WHERE clause arguments
        String[] selectionArgs = {maSach};
        Cursor c = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        Log.d("getSachById", "===>" + c.getCount());
        c.moveToFirst();
        while (!c.isAfterLast()) {
            s = new Sach();
            s.setMaSach(c.getString(0));
            s.setTenLoaiSach(c.getString(1));
            s.setTenSach(c.getString(2));
            s.setTacGia(c.getString(3));
            s.setNXB(c.getString(4));
            s.setGiaBia(c.getInt(5));
            s.setSoLuong(c.getInt(6));
            break;
        }
        c.close();
        return s;
    }

    //search top 10
    public ArrayList<Sach> getSachTop10(String month) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Sach> dsSach = new ArrayList<>();
//        if (Integer.parseInt(month) < 10) {
//            month = "0" + month;
//        }
        String sSQL = "SELECT maSach, SUM(soLuongMua) as soluongmua FROM HOADONCHITIET INNER JOIN HOADON " +
                "ON HOADON.maHoaDon = HOADONCHITIET.maHoaDon WHERE HOADON.ngayMua like '%/" + month + "/2019' " +
                "GROUP BY maSach ORDER BY soluongmua DESC LIMIT 3";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
//            Log.d("//=====", c.getString(0));
            Sach s = new Sach();
            s.setMaSach(c.getString(0));
            s.setSoLuong(c.getInt(1));
            s.setGiaBia(0);
            s.setTenLoaiSach("");
            s.setTenSach("");
            s.setTacGia("");
            s.setNXB("");
            dsSach.add(s);
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }
}
