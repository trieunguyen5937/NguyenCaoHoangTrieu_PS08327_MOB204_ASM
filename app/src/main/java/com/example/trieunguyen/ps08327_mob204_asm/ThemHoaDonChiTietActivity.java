package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.CartAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachSpinnerAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThemHoaDonChiTietActivity extends AppCompatActivity {

    EditText et_maHoaDon, et_maSach, et_soLuong;
    Button bt_them, bt_thanhToan;
    TextView tv_sum;
    Toolbar toolbar;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SachDAO sachDAO;
    public ArrayList<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter;
    int vnd = 0;
    HoaDon hoaDon = new HoaDon();
    //ĐỊNH DẠNG TIỀN
//    long vnd;
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    //ĐỊNH DẠNG NGÀY
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hoa_don_chi_tiet);

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

        adapter = new CartAdapter(this, dsHDCT);
        lvCart.setAdapter(adapter);
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            et_maHoaDon.setText(b.getString("MAHOADON"));
        }

        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADDHoaDonCHITIET();
            }
        });

        bt_thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanhToanHoaDon();
            }
        });
    }

    public void init() {
        et_maHoaDon = findViewById(R.id.et_maHoaDon);
        et_maSach = findViewById(R.id.et_maSach);
        et_soLuong = findViewById(R.id.et_soLuong);
        bt_them = findViewById(R.id.bt_them);
        bt_thanhToan = findViewById(R.id.bt_thanhToan);
        tv_sum = findViewById(R.id.tv_sum);
        lvCart = findViewById(R.id.lv_listview);
    }

    public void ADDHoaDonCHITIET() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ThemHoaDonChiTietActivity.this);
        sachDAO = new SachDAO(ThemHoaDonChiTietActivity.this);
        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                Sach sach = sachDAO.getSachById(et_maSach.getText().toString());
                if (sach != null) {
                    int pos = checkMaSach(dsHDCT, et_maSach.getText().toString());
                    HoaDon hoaDon = new HoaDon(et_maHoaDon.getText().toString(), new Date());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hoaDon, sach, Integer.parseInt(et_soLuong.getText().toString()));
                    if (pos >= 0) {
                        int soluong = dsHDCT.get(pos).getSoLuongMua();
                        hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(et_soLuong.getText().toString()));
                        dsHDCT.set(pos, hoaDonChiTiet);
                    } else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);
                } else {
                    Toast.makeText(getApplicationContext(), "Mã sách không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public void thanhToanHoaDon() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(ThemHoaDonChiTietActivity.this);
        //tinh tien
        vnd = 0;
        try {
            for (HoaDonChiTiet hd : dsHDCT) {
                hoaDonChiTietDAO.inserHoaDonChiTiet(hd);
                vnd = vnd + hd.getSoLuongMua() * hd.getSach().getGiaBia();
            }
            //ĐỊNH DẠNG LẠI SỐ TIỀN
            String thanhTien = currencyVN.format(vnd);
            tv_sum.setText(thanhTien);
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }

    public int checkMaSach(ArrayList<HoaDonChiTiet> lsHD, String maSach) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            HoaDonChiTiet hd = lsHD.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)) {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public int validation() {
        if (et_soLuong.getText().toString().isEmpty() || et_maSach.getText().toString().isEmpty()) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (et_maSach.getText().toString().isEmpty() || et_soLuong.getText().toString().isEmpty()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(ThemHoaDonChiTietActivity.this).create();
            alertDialog.setTitle("Chú ý!");
            alertDialog.setMessage("Vui lòng nhập đầy đủ thông tin HDCT");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            Intent intent = new Intent();
            Bundle b = new Bundle();
            b.putString("MASACH", et_maSach.getText().toString());
            b.putString("SOLUONG", et_soLuong.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (validation() < 0)
            Toast.makeText(this, "Bạn chưa thêm HDCT cho hóa đơn " + et_maHoaDon.getText().toString(), Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(ThemHoaDonChiTietActivity.this, HoaDonActivity.class);
//        Bundle b = new Bundle();
//        b.putString("SOLUONG", et_soLuong.getText().toString());
//        b.putString("MAHOADON", et_maHoaDon.getText().toString());
//        startActivityForResult(intent, 000);
//        finish();
    }
}
