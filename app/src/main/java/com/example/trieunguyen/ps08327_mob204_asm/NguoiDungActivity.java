package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.LoaiSachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.NguoiDungAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class NguoiDungActivity extends AppCompatActivity {

    ListView lv_nguoiDung;
    Toolbar toolbar;
    ImageView close;
    Button bt_logout;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> listNguoiDung;
    NguoiDungAdapter adapter;
    EditText et_username, et_password, et_phone, et_hoTen;
    Boolean toggleEdit = false;

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

        lv_nguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder updateDialog = new AlertDialog.Builder(NguoiDungActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.update_nguoidung_dialog, null);
                updateDialog.setView(v);
                final AlertDialog myDialog = updateDialog.create();
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final Button bt_update = v.findViewById(R.id.bt_update);
                Button bt_cancel = v.findViewById(R.id.bt_cancel);
                final TextView tv_edit = v.findViewById(R.id.tv_edit);

                final NguoiDung nguoiDung = listNguoiDung.get(i);

                // *** mã là khóa chính nên không được edit ***
                et_username = v.findViewById(R.id.et_username);
                et_username.setText(nguoiDung.getUsername());

                et_password = v.findViewById(R.id.et_password);
                et_password.setText(nguoiDung.getPassword());

                et_phone = v.findViewById(R.id.et_phone);
                et_phone.setText(nguoiDung.getPhone());

                et_hoTen = v.findViewById(R.id.et_hoTen);
                et_hoTen.setText(nguoiDung.getHoTen());

                //set toggle nút sửa trong dialog
                tv_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (toggleEdit == false) {
                            et_password.setEnabled(true);
                            et_phone.setEnabled(true);
                            et_hoTen.setEnabled(true);
                            bt_update.setEnabled(true);
                            bt_update.setTextColor(Color.BLACK);
                            tv_edit.setText("Xong");
                            toggleEdit = true;
                        } else {
                            et_password.setEnabled(false);
                            et_phone.setEnabled(false);
                            et_hoTen.setEnabled(false);
                            bt_update.setEnabled(false);
                            bt_update.setTextColor(Color.GRAY);
                            tv_edit.setText("Sửa");
                            toggleEdit = false;
                        }
                    }
                });

                final String unCu = et_username.getText().toString();
                final String pwCu = et_password.getText().toString();
                final String phoneCu = et_phone.getText().toString();
                final String hoTenCu = et_hoTen.getText().toString();

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String passMoi = et_password.getText().toString();
                        String hoTenMoi = et_hoTen.getText().toString();
                        String phoneMoi = et_phone.getText().toString();

                        if (validForm(passMoi, hoTenMoi, phoneMoi)) {

                            nguoiDung.setPassword(passMoi);
                            nguoiDung.setHoTen(hoTenMoi);
                            nguoiDung.setPhone(phoneMoi);

                            final NguoiDungDAO nguoiDungDAO = new NguoiDungDAO(NguoiDungActivity.this);
                            nguoiDungDAO.update(nguoiDung);
                            listNguoiDung = nguoiDungDAO.getAll();
                            adapter.notifyDataSetChanged();
                            myDialog.dismiss();
                            lv_nguoiDung.smoothScrollToPosition(i);

                            Snackbar snackbar = Snackbar.make(adapterView, "Đã cập nhật user", 4000);
                            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    nguoiDung.setPassword(unCu);
                                    nguoiDung.setPassword(pwCu);
                                    nguoiDung.setPhone(phoneCu);
                                    nguoiDung.setHoTen(hoTenCu);
                                    nguoiDungDAO.update(nguoiDung);
                                    listNguoiDung = nguoiDungDAO.getAll();
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(NguoiDungActivity.this, "Đã hoàn tác cập nhật", Toast.LENGTH_SHORT).show();
                                }
                            });
                            //SET MÀU CHỮ THÔNG BÁO
                            View sView = snackbar.getView();
                            TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.BLACK);
                            tv.setTextSize(16);
                            //SET MÀU BACKGROUND SNACKBAR
                            sView.setBackgroundColor(ContextCompat.getColor(NguoiDungActivity.this, R.color.snackbarBackground));
                            //SET MÀU CHỮ NÚT ACTION
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    }
                });

                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });

                myDialog.show();
            }
        });
    }

    public void init() {
        lv_nguoiDung = findViewById(R.id.lv_nguoiDung);
        listNguoiDung = new ArrayList<>();
        nguoiDungDAO = new NguoiDungDAO(NguoiDungActivity.this);
        listNguoiDung = nguoiDungDAO.getAll();
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
            case R.id.addUser:
                addUser();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return true;
    }

    private void addUser() {
        Intent intent = new Intent(NguoiDungActivity.this, ThemNguoiDungActivity.class);
        startActivityForResult(intent, 999);
    }

    private void logout() {
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

    private boolean validForm(String pass, String hoTen, String soDienThoai) {
        try {
            if (pass.trim().length() == 0) {
                et_password.requestFocus();
                throw new Exception("Vui lòng nhập password");
            }
            if (hoTen.trim().length() == 0) {
                et_hoTen.requestFocus();
                throw new Exception("Vui lòng nhập họ tên");
            }
            if (soDienThoai.trim().length() == 0) {
                et_phone.requestFocus();
                throw new Exception("Vui lòng nhập số điện thoại");
            }

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
