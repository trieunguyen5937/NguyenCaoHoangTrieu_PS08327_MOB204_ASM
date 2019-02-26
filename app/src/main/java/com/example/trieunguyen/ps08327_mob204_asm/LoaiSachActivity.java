package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
    EditText et_maLoaiSach, et_tenLoaiSach, et_moTa, et_viTri;
    Boolean toggleEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_sach);

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

        lv_loaiSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final AlertDialog.Builder updateDialog = new AlertDialog.Builder(LoaiSachActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.update_loaisach_dialog, null);
                updateDialog.setView(v);
                final AlertDialog myDialog = updateDialog.create();
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final Button bt_update = v.findViewById(R.id.bt_update);
                Button bt_cancel = v.findViewById(R.id.bt_cancel);
                final TextView tv_edit = v.findViewById(R.id.tv_edit);

                final LoaiSach loaiSach = listLoaiSach.get(i);

                // *** mã là khóa chính nên không được edit ***
                et_maLoaiSach = v.findViewById(R.id.et_maLoaiSach);
                et_maLoaiSach.setText(loaiSach.getMa());

                et_tenLoaiSach = v.findViewById(R.id.et_tenLoaiSach);
                et_tenLoaiSach.setText(loaiSach.getTen());

                et_moTa = v.findViewById(R.id.et_moTa);
                et_moTa.setText(loaiSach.getMota());

                et_viTri = v.findViewById(R.id.et_viTri);
                et_viTri.setText(String.valueOf(loaiSach.getVitri()));

                //set toggle nút sửa trong dialog
                tv_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggleEdit == false) {
                            et_tenLoaiSach.setEnabled(true);
                            et_moTa.setEnabled(true);
                            et_viTri.setEnabled(true);
                            bt_update.setEnabled(true);
                            bt_update.setTextColor(Color.BLACK);
                            tv_edit.setText("Xong");
                            toggleEdit = true;
                        } else {
                            et_tenLoaiSach.setEnabled(false);
                            et_moTa.setEnabled(false);
                            et_viTri.setEnabled(false);
                            bt_update.setEnabled(false);
                            bt_update.setTextColor(Color.GRAY);
                            tv_edit.setText("Sửa");
                            toggleEdit = false;
                        }
                    }
                });

                final String maCu = et_maLoaiSach.getText().toString();
                final String tenLoaiCu = et_tenLoaiSach.getText().toString();
                final String moTaCu = et_moTa.getText().toString();
                final int viTriCu = Integer.parseInt(et_viTri.getText().toString());

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        loaiSach.setMa(et_maLoaiSach.getText().toString());
                        loaiSach.setTen(et_tenLoaiSach.getText().toString());
                        loaiSach.setMota(et_moTa.getText().toString());
                        loaiSach.setVitri(Integer.parseInt(et_viTri.getText().toString()));

                        final LoaiSachDAO loaiSachDAO = new LoaiSachDAO(LoaiSachActivity.this);
                        loaiSachDAO.update(loaiSach);
                        listLoaiSach = loaiSachDAO.getAll();
                        adapter.notifyDataSetChanged();
                        myDialog.dismiss();

                        Snackbar snackbar = Snackbar.make(adapterView, "Đã cập nhật loại sách", 4000);
                        snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loaiSach.setMa(maCu);
                                loaiSach.setTen(tenLoaiCu);
                                loaiSach.setMota(moTaCu);
                                loaiSach.setVitri(viTriCu);
                                loaiSachDAO.update(loaiSach);
                                listLoaiSach = loaiSachDAO.getAll();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(LoaiSachActivity.this, "Đã hoàn tác cập nhật", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //SET MÀU CHỮ THÔNG BÁO
                        View sView = snackbar.getView();
                        TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
                        tv.setTextColor(Color.BLACK);
                        tv.setTextSize(16);
                        //SET MÀU BACKGROUND SNACKBAR
                        sView.setBackgroundColor(ContextCompat.getColor(LoaiSachActivity.this, R.color.snackbarBackground));
                        //SET MÀU CHỮ NÚT ACTION
                        snackbar.setActionTextColor(Color.RED);
                        snackbar.show();
                    }
                });

                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

//                updateDialog.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        String maCu = et_maLoaiSach.getText().toString();
//                        loaiSach.setMa(et_maLoaiSach.getText().toString());
//
//                        String tenLoaiCu = et_tenLoaiSach.getText().toString();
//                        loaiSach.setTen(et_tenLoaiSach.getText().toString());
//
//                        String moTaCu = et_moTa.getText().toString();
//                        loaiSach.setMota(et_moTa.getText().toString());
//
//                        int viTriCu = Integer.parseInt(et_viTri.getText().toString());
//                        loaiSach.setVitri(Integer.parseInt(et_viTri.getText().toString()));
//
//                        loaiSachDAO = new LoaiSachDAO(LoaiSachActivity.this);
//                        loaiSachDAO.update(loaiSach);
//                        listLoaiSach = loaiSachDAO.getAll();
//                        adapter.notifyDataSetChanged();
//                    }
//                });

//                updateDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });

                myDialog.show();

            }
        });
    }

    public void init() {
        lv_loaiSach = findViewById(R.id.lv_loaiSach);
        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(LoaiSachActivity.this);
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
        Intent intent = new Intent(this, ThemLoaiSachActivity.class);
        startActivityForResult(intent, 999);
        return true;
    }
}
