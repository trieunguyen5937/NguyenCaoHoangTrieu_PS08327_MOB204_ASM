package com.example.trieunguyen.ps08327_mob204_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ThongKeActivity extends AppCompatActivity {
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thống kê");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView tv_homNay = findViewById(R.id.tv_homNay);
        TextView tv_thangNay = findViewById(R.id.tv_thangNay);
        TextView tv_namNay = findViewById(R.id.tv_namNay);
        TextView tv_today = findViewById(R.id.tv_today);

        Calendar calendar = Calendar.getInstance();
        String strDate = sdf.format(calendar.getTime());

        tv_today.setText(strDate);

        hoaDonChiTietDAO = new HoaDonChiTietDAO(ThongKeActivity.this);

        String day = sdf.format(Calendar.getInstance().getTime());
        SimpleDateFormat strThang = new SimpleDateFormat("MM");
        String thang = strThang.format(Calendar.getInstance().getTime());

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String mNgay = currencyVN.format(hoaDonChiTietDAO.getDoanhThuTheoNgay(day));
        String mThang = currencyVN.format(hoaDonChiTietDAO.getDoanhThuTheoThang(thang));
        String mNam = currencyVN.format(hoaDonChiTietDAO.getDoanhThuTheoNam());

        tv_homNay.setText(mNgay);
        tv_thangNay.setText(mThang);
        tv_namNay.setText(mNam);
    }
}
