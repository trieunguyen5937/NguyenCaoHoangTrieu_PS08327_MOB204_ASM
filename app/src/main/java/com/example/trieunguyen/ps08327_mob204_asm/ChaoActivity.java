package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chao);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(ChaoActivity.this,
                    R.color.statusBarColorChao));
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new
                        Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}
