package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    NguoiDungAdapter nguoiDungAdapter = new NguoiDungAdapter();
    ListView lv_nguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nguoi_dung);
        lv_nguoiDung = findViewById(R.id.lv_nguoiDung);
        nguoiDungDAO = new NguoiDungDAO(ThemNguoiDungActivity.this);
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

        String tenDangNhap = et_tenDangNhap.getText().toString();
        String matKhau = et_matKhau.getText().toString();
        String xacNhan = et_xacNhanMatKhau.getText().toString();
        String hoTen = et_hoTen.getText().toString();
        String soDienThoai = et_soDienThoai.getText().toString();

        long i = -1;
        NguoiDung nguoiDung = null;
        if (validForm(tenDangNhap, matKhau, xacNhan, hoTen, soDienThoai)) {
            nguoiDung = new NguoiDung(tenDangNhap, matKhau, hoTen, soDienThoai);
            i = nguoiDungDAO.insert(nguoiDung);
            if (i > 0) {
                Toast.makeText(this, "Thêm người dùng " + tenDangNhap.toUpperCase() + " thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("NguoiDung", nguoiDung);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                final AlertDialog alertDialog = new AlertDialog.Builder(ThemNguoiDungActivity.this).create();
                alertDialog.setTitle("Chú ý!");
                alertDialog.setMessage("Username đã tồn tại, vui lòng thay đổi!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        et_tenDangNhap.requestFocus();
                    }
                });
                alertDialog.show();
            }
        }
        return true;
    }

    private boolean validForm(String username, String pass, String confirm, String hoTen, String soDienThoai) {
        try {
            if (username.trim().length() == 0) {
                et_tenDangNhap.requestFocus();
                throw new Exception("Vui lòng nhập username");
            }
            if (pass.trim().length() == 0) {
                et_matKhau.requestFocus();
                throw new Exception("Vui lòng nhập password");
            }
            if (!confirm.equals(pass)) {
                et_xacNhanMatKhau.requestFocus();
                throw new Exception("Vui lòng xác nhận lại mật khẩu");
            }
            if (hoTen.trim().length() == 0) {
                et_hoTen.requestFocus();
                throw new Exception("Vui lòng nhập họ tên");
            }
            if (soDienThoai.trim().length() == 0) {
                et_soDienThoai.requestFocus();
                throw new Exception("Vui lòng nhập số điện thoại");
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
