package com.example.trieunguyen.ps08327_mob204_asm.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public static final String TABLE_NAME = "HOADONCHITIET";
    public static final String SQL_HOA_DON_CHI_TIET =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    "maHDCT integer primary key autoincrement, " +
                    "maHoaDon text not null, " +
                    "maSach text not null, " +
                    "soLuongMua integer" +
                    ");";
//    public static final String strInsertHDCT1 = "Insert into " + TABLE_NAME +
//            " Values('1', '1', '1', '1')";

    public static final String TAG = "HoaDonChiTiet";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public HoaDonChiTietDAO(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public int inserHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
        ContentValues values = new ContentValues();
        values.put("maHoaDon", hoaDonChiTiet.getHoaDon().getMaHoaDon());
        values.put("maSach", hoaDonChiTiet.getSach().getMaSach());
        values.put("soLuongMua", hoaDonChiTiet.getSoLuongMua());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public int updateHoaDonChiTiet(HoaDonChiTiet hd) {
        ContentValues values = new ContentValues();
        values.put("maHDCT", hd.getMaHDCT());
        values.put("maHoaDon", hd.getHoaDon().getMaHoaDon());
        values.put("maSach", hd.getSach().getMaSach());
        values.put("soLuongMua", hd.getSoLuongMua());
        int result = db.update(TABLE_NAME, values, "maHDCT=?", new String[]{String.valueOf(hd.getMaHDCT())});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    public int deleteHoaDonChiTietByID(String maHDCT) {
        int result = db.delete(TABLE_NAME, "maHDCT=?", new String[]{maHDCT});
        if (result == 0)
            return -1;
        return 1;
    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTiet() {
        ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT maHDCT, HOADON.maHoaDon, HOADON.ngayMua, " +
                "SACH.maSach, SACH.tenLoaiSach, SACH.tenSach, SACH.tacGia, SACH.NXB, SACH.giaBia, " +
                "SACH.soLuong, HOADONCHITIET.soLuongMua FROM HOADONCHITIET INNER JOIN HOADON " +
                "on HOADONCHITIET.maHoaDon = HOADON.maHoaDon INNER JOIN SACH on SACH.maSach = HOADONCHITIET.maSach ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setMaHDCT(c.getInt(0));
                hoaDonChiTiet.setHoaDon(new HoaDon(c.getString(1), sdf.parse(c.getString(2))));
                hoaDonChiTiet.setSach(new Sach(c.getString(3), c.getString(4), c.getString(5),
                        c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
                hoaDonChiTiet.setSoLuongMua(c.getInt(10));
                listHoaDonChiTiet.add(hoaDonChiTiet);
                Log.d("//=====", hoaDonChiTiet.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return listHoaDonChiTiet;
    }

    public ArrayList<HoaDonChiTiet> getAllHoaDonChiTietByID(String maHoaDon) {
        ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT maHDCT, HOADON.maHoaDon,HOADON.ngayMua, " +
                "SACH.maSach, SACH.tenLoaiSach, SACH.tenSach, SACH.tacGia, SACH.NXB, SACH.giaBia, " +
                "SACH.soLuong, HOADONCHITIET.soLuongMua FROM HOADONCHITIET INNER JOIN HOADON " +
                "on HOADONCHITIET.maHoaDon = HOADON.maHoaDon INNER JOIN SACH " +
                "on SACH.maSach = HOADONCHITIET.maSach where HOADONCHITIET.maHoaDon ='" + maHoaDon + "'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setMaHDCT(c.getInt(0));
                hoaDonChiTiet.setHoaDon(new HoaDon(c.getString(1), sdf.parse(c.getString(2))));
                hoaDonChiTiet.setSach(new Sach(c.getString(3), c.getString(4), c.getString(5),
                        c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
                hoaDonChiTiet.setSoLuongMua(c.getInt(10));
                listHoaDonChiTiet.add(hoaDonChiTiet);
                Log.d("//=====", hoaDonChiTiet.toString());
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
        return listHoaDonChiTiet;
    }

    public boolean checkHoaDon(String maHoaDon) {
        //SELECT
        String[] columns = {"maHoaDon"};
        //WHERE clause
        String selection = "maHoaDon=?";
        //WHERE clause arguments
        String[] selectionArgs = {maHoaDon};
        Cursor c = null;
        try {
            c = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
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

    public double getDoanhThuTheoNgay(String day) {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(SACH.giaBia * HOADONCHITIET.soLuongMua)as tongtien FROM HOADONCHITIET " +
                "INNER JOIN HOADON on HOADONCHITIET.maHoaDon = HOADON.maHoaDon INNER JOIN SACH on SACH.maSach = HOADONCHITIET.maSach " +
                "Where HOADON.ngayMua = '" + day + "'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                doanhThu = c.getDouble(0);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public double getDoanhThuTheoThang(String month) {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(SACH.giaBia * HOADONCHITIET.soLuongMua)as tongtien FROM HOADONCHITIET " +
                "INNER JOIN HOADON on HOADONCHITIET.maHoaDon = HOADON.maHoaDon INNER JOIN SACH on SACH.maSach = HOADONCHITIET.maSach " +
                "Where HOADON.ngayMua like '%/" + month + "/2019'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                doanhThu = c.getDouble(0);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }

    public double getDoanhThuTheoNam() {
        double doanhThu = 0;
        String sSQL = "SELECT SUM(SACH.giaBia * HoaDonChiTiet.soLuongMua)as tongtien FROM HOADONCHITIET " +
                "INNER JOIN HOADON on HOADONCHITIET.maHoaDon = HOADON.maHoaDon INNER JOIN SACH on SACH.maSach = HOADONCHITIET.maSach " +
                "Where HOADON.ngayMua like '%/%/2019'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (!c.isAfterLast()) {
                doanhThu = c.getDouble(0);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doanhThu;
    }
}
