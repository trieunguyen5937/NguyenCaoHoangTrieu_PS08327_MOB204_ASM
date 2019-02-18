package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.LoaiSachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.NguoiDungAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class ThemNguoiDungActivity extends AppCompatActivity {

    EditText et_tenDangNhap, et_matKhau, et_xacNhanMatKhau, et_hoTen, et_soDienThoai;
    Toolbar toolbar;
    NguoiDungDAO nguoiDungDAO;
    List<NguoiDung> listNguoiDung = new ArrayList<>();
    NguoiDungAdapter nguoiDungAdapter = null;
    ListView lv_nguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        lv_nguoiDung = findViewById(R.id.lv_nguoiDung);
        nguoiDungDAO = new NguoiDungDAO(ThemNguoiDungActivity.this);

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

    private void init() {
        et_tenDangNhap = (EditText) findViewById(R.id.et_tenDangNhap);
        et_matKhau = (EditText) findViewById(R.id.et_matKhau);
        et_xacNhanMatKhau = (EditText) findViewById(R.id.et_xacNhanMatKhau);
        et_hoTen = (EditText) findViewById(R.id.et_hoTen);
        et_soDienThoai = (EditText) findViewById(R.id.et_soDienThoai);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        String tenDangNhap = et_tenDangNhap.getText().toString();
        String matKhau = et_matKhau.getText().toString();
        String xacNhan = et_xacNhanMatKhau.getText().toString();
        String hoTen = et_hoTen.getText().toString();
        String soDienThoai = et_soDienThoai.getText().toString();
        NguoiDung nguoiDung = new NguoiDung(tenDangNhap, matKhau, hoTen, soDienThoai);
        long i = nguoiDungDAO.insert(nguoiDung);
        if (i >= 0) {
            Toast.makeText(this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
//            intent.putExtra("NguoiDung", nguoiDung);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Thêm dữ không liệu thành công", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}
