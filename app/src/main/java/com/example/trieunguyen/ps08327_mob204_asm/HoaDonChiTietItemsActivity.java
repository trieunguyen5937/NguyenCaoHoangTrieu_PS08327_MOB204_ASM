package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.CartAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;

import java.util.ArrayList;

public class HoaDonChiTietItemsActivity extends AppCompatActivity {
    public ArrayList<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet_items);
        View parent_layout = findViewById(R.id.parent_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa đơn chi tiết");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lvCart = findViewById(R.id.lv_listview);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonChiTietItemsActivity.this);
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
            Toast.makeText(this, "Mã Hóa Đơn: " + b.getString("MAHOADON"), Toast.LENGTH_LONG).show();
            Log.d("MaHD", b.getString("MAHOADON") + "");
            adapter = new CartAdapter(HoaDonChiTietItemsActivity.this, dsHDCT);
            lvCart.setAdapter(adapter);

            lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

            if (dsHDCT.size() <= 0) {
                Snackbar snackbar = Snackbar.make(parent_layout, "Hóa đơn này chưa có hóa đơn chi tiết", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Thêm", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ThemHoaDonChiTietActivity.class);
                        //ĐƯA BUNDLE VÀO LẠI ĐỂ QUA ACTIVITY THÊM LẤY ĐƯƠC MÃ HÓA ĐƠN
                        b.putString("MAHOADON", b.getString("MAHOADON"));
                        intent.putExtras(b);
                        startActivityForResult(intent, 999);
                    }
                });
                //SET MÀU CHỮ THÔNG BÁO
                View sView = snackbar.getView();
                TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(15);
                //SET MÀU BACKGROUND SNACKBAR
                sView.setBackgroundColor(ContextCompat.getColor(HoaDonChiTietItemsActivity.this, R.color.snackbarBackground));
                //SET MÀU CHỮ NÚT ACTION
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoadon_oneitem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), ThemHoaDonChiTietActivity.class);
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        //ĐƯA BUNDLE VÀO LẠI ĐỂ QUA ACTIVITY THÊM LẤY ĐƯƠC MÃ HÓA ĐƠN
        b.putString("MAHOADON", b.getString("MAHOADON"));
        intent.putExtras(b);
        startActivityForResult(intent, 999);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lvCart = findViewById(R.id.lv_listview);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonChiTietItemsActivity.this);
        Intent in = getIntent();
        final Bundle b = in.getExtras();
        if (b != null) {
            dsHDCT = hoaDonChiTietDAO.getAllHoaDonChiTietByID(b.getString("MAHOADON"));
//            Toast.makeText(this, "Mã Hóa Đơn: " + b.getString("MAHOADON"), Toast.LENGTH_LONG).show();
            Log.d("MaHD", b.getString("MAHOADON") + "");
            adapter = new CartAdapter(HoaDonChiTietItemsActivity.this, dsHDCT);
            lvCart.setAdapter(adapter);
        }
    }
}
