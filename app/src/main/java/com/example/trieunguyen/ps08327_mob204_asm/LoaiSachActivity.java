package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.LoaiSachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.database.DbHelper;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachActivity extends AppCompatActivity {

    ListView lv_loaiSach;
    Toolbar toolbar;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachAdapter adapter;
    LoaiSachDAO loaiSachDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_sach);
        loaiSachDAO = new LoaiSachDAO(LoaiSachActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loại sách");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        init();

        adapter = new LoaiSachAdapter(LoaiSachActivity.this, listLoaiSach);
        lv_loaiSach.setAdapter(adapter);
    }

    public void init() {
        lv_loaiSach = findViewById(R.id.lv_loaiSach);

        listLoaiSach = new ArrayList<>();
        listLoaiSach = loaiSachDAO.getAll();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 & resultCode == RESULT_OK) {
            LoaiSach loaiSach = (LoaiSach) data.getSerializableExtra("LoaiSach");
            listLoaiSach.add(loaiSach);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them_loaisach, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //open dialog
        Intent intent = new Intent(this, ThemLoaiSachActivity.class);
        startActivityForResult(intent, 999);
        return true;
    }
}
