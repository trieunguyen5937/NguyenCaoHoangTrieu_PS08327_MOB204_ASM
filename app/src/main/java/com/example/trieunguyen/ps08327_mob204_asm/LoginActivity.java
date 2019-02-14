package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;

public class LoginActivity extends AppCompatActivity {

    Button btLogin, btCancel;
    Button bt_exit;
    ImageView close;
    TextInputLayout tiUserName, tiPassWord;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = findViewById(R.id.btnLogin);
        btCancel = findViewById(R.id.btnCancel);
        tiUserName = findViewById(R.id.tiUserName);
        tiPassWord = findViewById(R.id.tiPassword);
        nguoiDungDAO = new NguoiDungDAO(LoginActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,
                    R.color.statusBarColorLogin));
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = tiUserName.getEditText().getText().toString();
                String pw = tiPassWord.getEditText().getText().toString();
                if(nguoiDungDAO.checkLogin(un, pw)) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại\nKiểm tra lại thông tin đăng nhập", Toast.LENGTH_LONG).show();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tiUserName.setTe
            }
        });

    }

    @Override
    public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Xác nhận")
//                .setMessage("Bạn có muốn thoát ứng dụng?")
//                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
        //chỉ rời app, vẫn chạy nền
//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//
//                        //đóng hẳn app
//                        finish();
//                        moveTaskToBack(true);
//                    }
//                })
//                .setNegativeButton("Không", null)
//                .show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_exit_app, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        close = (ImageView) view.findViewById(R.id.close);
        bt_exit = (Button) view.findViewById(R.id.bt_exit);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                moveTaskToBack(true);
            }
        });

        alertDialog.show();
    }



}
