package com.example.trieunguyen.ps08327_mob204_asm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.HoaDonAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HoaDonActivity extends AppCompatActivity {
    ArrayList<HoaDon> listHoaDon = new ArrayList<>();
    ArrayList<HoaDonChiTiet> listHoaDonChiTiet = new ArrayList<>();
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;
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

        init();

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

        lv_hoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {
                final AlertDialog.Builder updateDialog = new AlertDialog.Builder(HoaDonActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.update_hoadon_dialog, null);
                updateDialog.setView(v);
                final AlertDialog myDialog = updateDialog.create();
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final Button bt_update = v.findViewById(R.id.bt_update);
                Button bt_cancel = v.findViewById(R.id.bt_cancel);

                final HoaDon hoaDon = listHoaDon.get(i);

                // *** mã là khóa chính nên không được edit ***
                final EditText et_maHoaDon = v.findViewById(R.id.et_maHoaDon);
                final String maHDCu = hoaDon.getMaHoaDon();
                et_maHoaDon.setText(maHDCu);

                final EditText et_ngay = v.findViewById(R.id.et_ngay);
                final Date ngayCu = hoaDon.getNgayMua();
                et_ngay.setText(sdf.format(ngayCu));

                ImageView iv_chooseDate = v.findViewById(R.id.iv_chooseDate);

                iv_chooseDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar calendar = Calendar.getInstance();
                        DatePickerDialog datePickerDialog = new DatePickerDialog(HoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                        et_ngay.setText(dayOfMonth + " / " + (month + 1) + " / " + year);
                                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
//                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                                et_ngay.setText(sdf.format(cal.getTime()));
                            }
                        },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        );
                        datePickerDialog.show();
                    }
                });

                bt_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String maHDMoi = et_maHoaDon.getText().toString();
                        String ngayMoi = et_ngay.getText().toString();
                        try {
                            if (!ngayMoi.isEmpty()) {
                                hoaDon.setMaHoaDon(maHDMoi);
                                hoaDon.setNgayMua(sdf.parse(ngayMoi));

                                final HoaDonDAO hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
                                hoaDonDAO.updateHoaDon(hoaDon);
                                listHoaDon = hoaDonDAO.getAllHoaDon();
                                hoaDonAdapter.notifyDataSetChanged();
                                myDialog.dismiss();

                                final Snackbar snackbar = Snackbar.make(adapterView, "Đã cập nhật hóa đơn", 4000);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        snackbar.dismiss();
                                    }
                                });
                                //SET MÀU CHỮ THÔNG BÁO
                                View sView = snackbar.getView();
                                TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
                                tv.setTextColor(Color.BLACK);
                                tv.setTextSize(16);
                                //SET MÀU BACKGROUND SNACKBAR
                                sView.setBackgroundColor(ContextCompat.getColor(HoaDonActivity.this, R.color.snackbarBackground));
                                //SET MÀU CHỮ NÚT ACTION
                                snackbar.setActionTextColor(Color.RED);
                                snackbar.show();
                            } else {
                                Toast.makeText(HoaDonActivity.this, "Vui lòng nhập ngày hóa đơn", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(HoaDonActivity.this, "Không định dạng ngày được " + ngayMoi + e, Toast.LENGTH_SHORT).show();

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

                return false;
            }
        });
    }

    public void init() {
        lv_hoaDon = findViewById(R.id.lv_hoaDon);
        listHoaDon = new ArrayList<>();
        hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
        try {
            listHoaDon = hoaDonDAO.getAllHoaDon();
        } catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
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
        et_maHoaDon.requestFocus();
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
                        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
//                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        et_ngay.setText(sdf.format(cal.getTime()));
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
//                            b.putString("NGAYHOADON", et_ngay.getText().toString());
                            intent.putExtras(b);
                            startActivityForResult(intent, 111);
                            myDialog.dismiss();
                        } else {
                            Toast.makeText(HoaDonActivity.this, "Mã hóa đơn đã tồn tại!", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 000 & resultCode == RESULT_CANCELED) {
            init();
            hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this, listHoaDon);
            lv_hoaDon.setAdapter(hoaDonAdapter);
        }
    }
}
