package com.example.trieunguyen.ps08327_mob204_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ThemSachActivity extends AppCompatActivity {

    EditText et_maSach, et_tenSach, et_tacGia, et_NXB, et_giaBia, et_soLuong;
    Spinner sp_loaiSach;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        et_maSach = findViewById(R.id.et_maSach);
        et_tenSach = findViewById(R.id.et_tenSach);
        et_tacGia = findViewById(R.id.et_tacGia);
        et_NXB = findViewById(R.id.et_NXB);
        et_giaBia = findViewById(R.id.et_giaBia);
        et_soLuong = findViewById(R.id.et_soLuong);
        sp_loaiSach = findViewById(R.id.sp_loaiSach);

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
        //
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        return true;
    }
}
