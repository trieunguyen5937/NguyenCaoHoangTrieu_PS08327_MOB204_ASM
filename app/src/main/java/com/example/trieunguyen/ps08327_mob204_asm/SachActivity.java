package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class SachActivity extends AppCompatActivity {

    Boolean toggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý sách");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sach, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timKiem:
                timSach();
                break;
            case R.id.them:
                themSach();
                break;
        }
        return true;
    }

    private void timSach() {
        FrameLayout frameLayout_search = findViewById(R.id.framelayout_search);
        final EditText et_searchField = findViewById(R.id.et_searchField);
        Button bt_clearSearchField = findViewById(R.id.bt_clearSearchField);
        //toggle để ẩn hiện search field
        if (toggle == false) {
            frameLayout_search.setVisibility(View.VISIBLE);
            et_searchField.requestFocus();
            toggle = true;
        } else {
            frameLayout_search.setVisibility(View.GONE);
            toggle = false;
        }

        bt_clearSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear nội dung search
                et_searchField.setText("");
            }
        });

        //hàm tìm kiếm
    }

    private void themSach() {
        Intent intent = new Intent(SachActivity.this, ThemSachActivity.class);
        startActivity(intent);
    }
}
