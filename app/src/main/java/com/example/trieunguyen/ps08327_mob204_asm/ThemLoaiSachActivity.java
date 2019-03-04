package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.LoaiSachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.utilities.ClearableEditText;

import java.util.ArrayList;
import java.util.List;

public class ThemLoaiSachActivity extends AppCompatActivity {

    EditText et_maLoaiSach, et_tenLoaiSach, et_moTa, et_viTri;
    Toolbar toolbar;
    LoaiSachDAO loaiSachDAO;
    List<LoaiSach> listLoaiSach = new ArrayList<>();
    LoaiSachAdapter loaiSachAdapter = null;
    ListView lv_loaiSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_sach);
        lv_loaiSach = findViewById(R.id.lv_loaiSach);
        loaiSachDAO = new LoaiSachDAO(ThemLoaiSachActivity.this);
        init();

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
        et_maLoaiSach = (EditText) findViewById(R.id.et_maLoaiSach);
        et_tenLoaiSach = (EditText) findViewById(R.id.et_tenLoaiSach);
        et_moTa = (EditText) findViewById(R.id.et_moTa);
        et_viTri = (EditText) findViewById(R.id.et_viTri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String maLoai = et_maLoaiSach.getText().toString();
        String tenLoai = et_tenLoaiSach.getText().toString();
        String moTa = et_moTa.getText().toString();
        String viTri = et_viTri.getText().toString();
        long i = -1;
        LoaiSach loaiSach = null;
        if (validForm(maLoai, tenLoai, moTa, viTri)) {
            int vt = Integer.parseInt(viTri);
            loaiSach = new LoaiSach(maLoai, tenLoai, moTa, vt);
            i = loaiSachDAO.insert(loaiSach);
            if (i > 0) {
                Toast.makeText(this, "Thêm loại sách " + tenLoai.toUpperCase() + " thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.putExtra("LoaiSach", loaiSach);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                final AlertDialog alertDialog = new AlertDialog.Builder(ThemLoaiSachActivity.this).create();
                alertDialog.setTitle("Chú ý!");
                alertDialog.setMessage("Mã loại sách đã tồn tại!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        et_maLoaiSach.requestFocus();
                    }
                });
                alertDialog.show();
            }
        }
        return true;
    }

    private boolean validForm(String maLoai, String tenLoai, String moTa, String viTri) {
        try {
            if (maLoai.trim().length() == 0) {
                et_maLoaiSach.requestFocus();
                throw new Exception("Vui lòng nhập mã loại");
            }
            if (tenLoai.trim().length() == 0) {
                et_tenLoaiSach.requestFocus();
                throw new Exception("Vui lòng nhập tên loại");
            }
            if (moTa.trim().length() == 0) {
                et_moTa.requestFocus();
                throw new Exception("Vui lòng nhập mô tả");
            }
            if (viTri.trim().length() == 0) {
                et_viTri.requestFocus();
                throw new Exception("Vui lòng nhập vị trí");
            }

            int vt = Integer.parseInt(viTri.trim());
            if (vt < 0) {
                throw new Exception("Vị trí không được là số âm");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vị trí phải là số", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
