package com.example.trieunguyen.ps08327_mob204_asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

public class DbHelper extends SQLiteOpenHelper {
    public static NguoiDung USER = null;
    public DbHelper(Context context) {
        super(context, "DuAnMau", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LoaiSachDAO.SQL_LOAI_SACH);
        db.execSQL(LoaiSachDAO.strInsertLS1);
        db.execSQL(LoaiSachDAO.strInsertLS2);
//        db.execSQL(SachDAO.SQL_SACH);
        db.execSQL(NguoiDungDAO.SQL_NGUOI_DUNG);
        db.execSQL(NguoiDungDAO.strInsert1);
        db.execSQL(NguoiDungDAO.strInsert2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + LoaiSachDAO.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + SachDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NguoiDungDAO.TABLE_NAME);
    }
}
