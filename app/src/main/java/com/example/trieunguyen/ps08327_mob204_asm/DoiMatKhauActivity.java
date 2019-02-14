package com.example.trieunguyen.ps08327_mob204_asm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DoiMatKhauActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText et_newPassword, et_confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        et_newPassword = findViewById(R.id.et_newPassword);
        et_confirmPassword = findViewById(R.id.et_confirmPassword);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //
        Toast.makeText(this, "Password updated", Toast.LENGTH_SHORT).show();
        return true;
    }
}
