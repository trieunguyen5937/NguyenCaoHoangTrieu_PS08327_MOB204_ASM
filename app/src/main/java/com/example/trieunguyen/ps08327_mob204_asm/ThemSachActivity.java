package com.example.trieunguyen.ps08327_mob204_asm;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        sachDAO = new SachDAO(ThemSachActivity.this);
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

        LoaiSach loaiSach = null;
        try {
            int index = (int) sp_loaiSach.getSelectedItemId();
            loaiSach = listLoaiSach.get(index);
        } catch (Exception e) {
            Toast.makeText(this, "Danh sách loại sách rỗng, vui lòng thêm loại sách", Toast.LENGTH_SHORT).show();
        }
        String tenLoaiSach = loaiSach.getTen();

        String tacGia = et_tacGia.getText().toString();
        String nxb = et_NXB.getText().toString();
        int giaBia = Integer.parseInt(et_giaBia.getText().toString());
        int soLuong = Integer.parseInt(et_soLuong.getText().toString());
        Sach sach = new Sach(maLoai, tenLoaiSach, tenSach, tacGia, nxb, giaBia, soLuong);
        long i = sachDAO.insert(sach);
        if (i > 0) {
            Toast.makeText(this, "Thêm sách " + tenSach.toUpperCase() + " thành công", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("Sach", sach);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Thêm sách mới không thành công", Toast.LENGTH_SHORT).show();
            finish();
        }

        return true;
    }
}
