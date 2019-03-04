package com.example.trieunguyen.ps08327_mob204_asm;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachRecyclerViewAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.adapter.SachSpinnerAdapter;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.util.ArrayList;

public class SachActivity extends AppCompatActivity {

    ListView lv_sach;
    RecyclerView rv_sach;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    SachAdapter adapter;
    SachRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;

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

        init();

        //CÁCH 1: DÙNG LISTVIEW
//        adapter = new SachAdapter(SachActivity.this, listSach);
//        lv_sach.setAdapter(recyclerViewAdapter);

        //CÁCH 2: DÙNG RECYCLERVIEW
        recyclerViewAdapter = new SachRecyclerViewAdapter(SachActivity.this, listSach);
        rv_sach.setAdapter(recyclerViewAdapter);
        rv_sach.setLayoutManager(new LinearLayoutManager(SachActivity.this));

    }

    public void init() {
//        lv_sach = findViewById(R.id.lv_sach); //cách 1
        rv_sach = findViewById(R.id.rv_sach); //cách 2
        listSach = new ArrayList<>();
        sachDAO = new SachDAO(SachActivity.this);
        listSach = sachDAO.getAll();
//        Toast.makeText(this, ""+listSach.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 & resultCode == RESULT_OK) {
            Sach sach = (Sach) data.getSerializableExtra("Sach");
            listSach.add(sach);
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sach, menu);

        //SEARCH
        //tham khảo: https://codinginflow.com/tutorials/android/searchview-recyclerview
        //và https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/
        MenuItem searchItem = menu.findItem(R.id.timKiem);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Nhập tên sách cần tìm");

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
                recyclerViewAdapter.getFilter().filter(input);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timKiem:
//                timSach();
                break;
            case R.id.them:
                themSach();
                break;
        }
        return true;
    }

    private boolean themSach() {
        Intent intent = new Intent(SachActivity.this, ThemSachActivity.class);
        startActivityForResult(intent, 999);
        return true;
    }
}
