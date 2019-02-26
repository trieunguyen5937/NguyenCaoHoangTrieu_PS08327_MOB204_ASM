package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "HOADON";
    public static final String SQL_HOA_DON =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maHoaDon text primary key," +
                    "ngayMua date" +
                    ");";
    public static final String TAG = "HoaDonDAO";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int insert(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDon.getMaHoaDon());
        values.put("ngayMua", sdf.format(hoaDon.getNgayMua()));
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateHoaDon(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDon.getMaHoaDon());
        values.put("ngayMua", hoaDon.getNgayMua().toString());
        int result = db.update(TABLE_NAME, values, "maHoaDon=?", new
                String[]{hoaDon.getMaHoaDon()});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int deleteHoaDonByID(String maHoaDon) {
        int result = db.delete(TABLE_NAME, "maHoaDon=?", new String[]{maHoaDon});
        if (result == 0)
            return -1;
        return 1;
    }

    public List<HoaDon> getAllHoaDon() throws ParseException {
        ArrayList<HoaDon> listHoaDon = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHoaDon(c.getString(0));
            hoaDon.setNgayMua(sdf.parse(c.getString(1)));
            listHoaDon.add(hoaDon);
            Log.d("//=====", hoaDon.toString());
            c.moveToNext();
        }
        c.close();
        return listHoaDon;
    }

    public void getById() {

    }

    public void search() {

    }
}
