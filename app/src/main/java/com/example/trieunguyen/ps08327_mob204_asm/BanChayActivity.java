package com.example.trieunguyen.ps08327_mob204_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachRecyclerViewAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.util.ArrayList;

public class BanChayActivity extends AppCompatActivity {

    public static ArrayList<Sach> listSach = new ArrayList<>();
    ListView lv_sachBanChay;
    SachAdapter adapter;
    SachDAO sachDAO;
    EditText et_searchField;
    Sach sach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_chay);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top sách bán chạy");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_searchField = findViewById(R.id.et_searchField);
        lv_sachBanChay = findViewById(R.id.lv_sachBanChay);
    }

//    public void VIEW_SACH_TOP_10(View view) {
//        String str = et_searchField.getText().toString();
//
//        if (str.isEmpty()) {
//            Toast.makeText(this, "Vui lòng nhập tháng cần tìm", Toast.LENGTH_SHORT).show();
//        } else if (Integer.parseInt(str) > 13 || Integer.parseInt(str) < 0) {
//            Toast.makeText(getApplicationContext(), "Không đúng định dạng tháng (1 - 12) ", Toast.LENGTH_SHORT).show();
//        } else {
//            sachDAO = new SachDAO(BanChayActivity.this);
//            listSach = sachDAO.getSachTop10(str);
////            listSach = sachDAO.getAll();
//            adapter = new SachAdapter(this, listSach);
//            lv_sachBanChay.setAdapter(adapter);
//            Toast.makeText(this, listSach.size() + "", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void VIEW_SACH_TOP_10(View view) {
        String str = et_searchField.getText().toString();
        sachDAO = new SachDAO(BanChayActivity.this);
        int thang = -1;
        try {
            thang = Integer.parseInt(str);
            if (thang > 13 || thang < 0) {
                Toast.makeText(getApplicationContext(), "Không đúng định dạng tháng (1-12) ", Toast.LENGTH_SHORT).show();
            } else if (thang < 10) {
                listSach = sachDAO.getSachTop10("0" + thang);
                adapter = new SachAdapter(BanChayActivity.this, listSach);
                lv_sachBanChay.setAdapter(adapter);
            } else {
                listSach = sachDAO.getSachTop10("" + thang);
                adapter = new SachAdapter(BanChayActivity.this, listSach);
                lv_sachBanChay.setAdapter(adapter);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Vui lòng nhập tháng cần xem", Toast.LENGTH_SHORT).show();
        }
    }
}
