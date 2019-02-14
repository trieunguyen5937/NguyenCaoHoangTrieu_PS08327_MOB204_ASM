package com.example.trieunguyen.ps08327_mob204_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ThemNguoiDungActivity extends AppCompatActivity {

    EditText et_tenDangNhap, et_matKhau, et_xacNhanMatKhau, et_hoTen, et_moTa;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        et_tenDangNhap = findViewById(R.id.et_tenDangNhap);
        et_matKhau = findViewById(R.id.et_matKhau);
        et_xacNhanMatKhau = findViewById(R.id.et_xacNhanMatKhau);
        et_hoTen = findViewById(R.id.et_hoTen);
        et_moTa = findViewById(R.id.et_moTa);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        return true;
    }
}
