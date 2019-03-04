package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachSpinnerAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.util.ArrayList;

public class ThemSachActivity extends AppCompatActivity {

    EditText et_maSach, et_tenSach, et_tacGia, et_NXB, et_giaBia, et_soLuong;
    Spinner sp_loaiSach;
    Toolbar toolbar;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    SachAdapter sachAdapter = null;
    ListView lv_sach;
    ArrayList<LoaiSach> listLoaiSach = new ArrayList<>(); //LIST LOẠI ĐỂ SET SPINNER
    String tenLoaiSach = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        loaiSachDAO = new LoaiSachDAO(ThemSachActivity.this);

        init();

        listLoaiSach = loaiSachDAO.getAll();
        //SET SPINNER
        SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(ThemSachActivity.this, listLoaiSach);
        sp_loaiSach.setAdapter(sachSpinnerAdapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_cancel_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void init() {
        et_maSach = findViewById(R.id.et_maSach);
        et_tenSach = findViewById(R.id.et_tenSach);
        et_tacGia = findViewById(R.id.et_tacGia);
        et_NXB = findViewById(R.id.et_NXB);
        et_giaBia = findViewById(R.id.et_giaBia);
        et_soLuong = findViewById(R.id.et_soLuong);
        sp_loaiSach = findViewById(R.id.sp_loaiSach);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String maLoai = et_maSach.getText().toString();
        String tenSach = et_tenSach.getText().toString();

        //LẤY SPINNER CÁCH 1
//        LoaiSach loaiSach = null;
//        try {
//            int index = (int) sp_loaiSach.getSelectedItemId();
//            loaiSach = listLoaiSach.get(index);
//        } catch (Exception e) {
//            Toast.makeText(this, "Danh sách loại sách rỗng, vui lòng thêm loại sách", Toast.LENGTH_SHORT).show();
//        }
//        String tenLoaiSach = loaiSach.getTen();

        //LẤY SPINNER CÁCH 2
        sp_loaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tenLoaiSach = listLoaiSach.get(sp_loaiSach.getSelectedItemPosition()).getTen();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String tacGia = et_tacGia.getText().toString();
        String nxb = et_NXB.getText().toString();
        String giaBia = et_giaBia.getText().toString();
        String soLuong = et_soLuong.getText().toString();
        sachDAO = new SachDAO(ThemSachActivity.this);
//        sach = new Sach(maLoai, tenLoaiSach, tenSach, tacGia, nxb, giaBia, soLuong);
        long i = -1;
        Sach sach = null;
        if (validForm(maLoai, tenSach, tacGia, nxb, giaBia, soLuong)) {
            int giabia = Integer.parseInt(giaBia);
            int soluong = Integer.parseInt(soLuong);
            sach = new Sach(maLoai, tenLoaiSach, tenSach, tacGia, nxb, giabia, soluong);
            i = sachDAO.insert(sach);
            if (i > 0) {
                Toast.makeText(this, "Thêm sách " + tenSach.toUpperCase() + " thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("Sach", sach);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Thêm sách mới không thành công", Toast.LENGTH_SHORT).show();
                final AlertDialog alertDialog = new AlertDialog.Builder(ThemSachActivity.this).create();
                alertDialog.setTitle("Chú ý!");
                alertDialog.setMessage("Mã sách đã tồn tại!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        et_maSach.requestFocus();
                    }
                });
                alertDialog.show();
            }
        }
        return true;
    }

    private boolean validForm(String maSach, String tenSach, String tacGia, String nxb, String giaBia, String soLuong) {
        try {
            if (maSach.trim().length() == 0) {
                et_maSach.requestFocus();
                throw new Exception("Vui lòng nhập mã sách");
            }
            if (tenSach.trim().length() == 0) {
                et_tenSach.requestFocus();
                throw new Exception("Vui lòng nhập tên sách");
            }
            if (tacGia.trim().length() == 0) {
                et_tacGia.requestFocus();
                throw new Exception("Vui lòng nhập tác giả");
            }
            if (nxb.trim().length() == 0) {
                et_NXB.requestFocus();
                throw new Exception("Vui lòng nhập NXB");
            }
            if (giaBia.trim().length() == 0) {
                et_giaBia.requestFocus();
                throw new Exception("Vui lòng nhập giá bìa");
            }
            int giabia = Integer.parseInt(giaBia.trim());
            if (giabia < 0) {
                throw new Exception("Giá bìa không được là số âm");
            }
            if (soLuong.trim().length() == 0) {
                et_giaBia.requestFocus();
                throw new Exception("Vui lòng nhập số lượng");
            }
            int soluong = Integer.parseInt(giaBia.trim());
            if (soluong < 0) {
                throw new Exception("Số lượng không được là số âm");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá bìa và số lượng phải là số", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
