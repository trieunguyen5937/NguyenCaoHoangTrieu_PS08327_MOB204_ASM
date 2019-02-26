package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;

import java.util.ArrayList;

public class SachSpinnerAdapter extends BaseAdapter {
    Context c;
    ArrayList<LoaiSach> listLoai_spinner;

    public SachSpinnerAdapter(Context c, ArrayList<LoaiSach> listLoai_spinner) {
        this.c = c;
        this.listLoai_spinner = listLoai_spinner;
    }

    @Override
    public int getCount() {
        return listLoai_spinner.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = ((Activity)c).getLayoutInflater();
        view = inflater.inflate(R.layout.spinner_sach, null);

        TextView sp_loaiSach = view.findViewById(R.id.sp_loaiSach);

        final LoaiSach loaiSach = listLoai_spinner.get(position);
        sp_loaiSach.setText(loaiSach.getTen());

        return view;
    }
}
