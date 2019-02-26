package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.HoaDonAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HoaDonActivity extends AppCompatActivity {
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    ListView lv_hoaDon;
    HoaDonAdapter hoaDonAdapter;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa đơn");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this, listHoaDon);
        lv_hoaDon.setAdapter(hoaDonAdapter);

        lv_hoaDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HoaDon hoaDon = (HoaDon) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(HoaDonActivity.this, HoaDonChiTietItemsActivity.class);
                Bundle b = new Bundle();
                b.putString("MAHOADON", hoaDon.getMaHoaDon());
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void init() throws ParseException {
        lv_hoaDon = findViewById(R.id.lv_hoaDon);
        listHoaDon = new ArrayList<>();
        hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
        listHoaDon = (ArrayList<HoaDon>) hoaDonDAO.getAllHoaDon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoadon, menu);

        //SEARCH
        //tham khảo: https://codinginflow.com/tutorials/android/searchview-recyclerview
        //và https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/
        MenuItem searchItem = menu.findItem(R.id.timKiem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Tìm mã hóa đơn");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                String input = query.toLowerCase();
//                recyclerViewAdapter.getFilter().filter(input);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                String input = query.toLowerCase();
                hoaDonAdapter.getFilter().filter(input);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timKiem:
                break;
            case R.id.them:
                themHoaDon();
                break;
        }
        return true;
    }

    private void themHoaDon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.add_hoadon_dialog, null);
        builder.setView(view);
        final AlertDialog myDialog = builder.create();
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button bt_continue = view.findViewById(R.id.bt_continue);
        Button bt_cancel = view.findViewById(R.id.bt_cancel);

        final EditText et_maHoaDon = view.findViewById(R.id.et_maHoaDon);
        final EditText et_ngay = view.findViewById(R.id.et_ngay);
        ImageView iv_chooseDate = view.findViewById(R.id.iv_chooseDate);

        iv_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(HoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        et_ngay.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                        et_ngay.setText(sdf.format(calendar.getTime()));
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();
            }
        });

        bt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
                try {
                    if (et_maHoaDon.getText().toString().isEmpty() || et_ngay.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        HoaDon hoaDon = new HoaDon(et_maHoaDon.getText().toString(), sdf.parse(et_ngay.getText().toString()));
                        if (hoaDonDAO.insert(hoaDon) > 0) {
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HoaDonActivity.this, ThemHoaDonChiTietActivity.class);
                            Bundle b = new Bundle();
                            b.putString("MAHOADON", et_maHoaDon.getText().toString());
                            intent.putExtras(b);
                            startActivity(intent);
                            myDialog.dismiss();
                        } else {
                            Toast.makeText(HoaDonActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 999 & resultCode == RESULT_OK) {
//            HoaDon hoaDon = (HoaDon) data.getSerializableExtra("HoaDon");
//            listHoaDon.add(hoaDon);
//            hoaDonAdapter.notifyDataSetChanged();
//        }
//    }
}
