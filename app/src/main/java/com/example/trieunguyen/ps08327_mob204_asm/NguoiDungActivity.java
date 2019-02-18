package com.example.trieunguyen.ps08327_mob204_asm;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.LoaiSachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.NguoiDungAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungActivity extends AppCompatActivity {

    ListView lv_nguoiDung;
    Toolbar toolbar;
    FloatingActionButton fab;
    ImageView close;
    Button bt_logout;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDungAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Người dùng");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        init();

        adapter = new NguoiDungAdapter(NguoiDungActivity.this, listNguoiDung);
        lv_nguoiDung.setAdapter(adapter);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NguoiDungActivity.this, ThemNguoiDungActivity.class);
                startActivity(intent);
            }
        });

    }

    public void init() {
        lv_nguoiDung = findViewById(R.id.lv_nguoiDung);

        listNguoiDung = new ArrayList<>();
//        listNguoiDung = nguoiDungDAO.getAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 & resultCode == RESULT_OK) {
            NguoiDung nguoiDung = (NguoiDung) data.getSerializableExtra("NguoiDung");
            listNguoiDung.add(nguoiDung);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nguoidung, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updatePass:
                updatePassword();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return true;
    }

    private void updatePassword() {
        Intent intent = new Intent(this, DoiMatKhauActivity.class);
        startActivity(intent);
    }

    private void logout() {
//        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(NguoiDungActivity.this);
//        confirmDialog.setTitle("Xác nhận đăng xuất");
//        confirmDialog.setMessage("Bạn có muốn đăng xuất khỏi tài khoản hiện tại?");
//
//        confirmDialog.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, final int i) {
//                Intent intent = new Intent(NguoiDungActivity.this, LoginActivity.class);
//                startActivity(intent);
//                Toast.makeText(NguoiDungActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        confirmDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        confirmDialog.show();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_logout_user, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        close = (ImageView) view.findViewById(R.id.close);
        bt_logout = (Button) view.findViewById(R.id.bt_logout);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        bt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NguoiDungActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(NguoiDungActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.show();
    }
}
