package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.ThemLoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.ThemNguoiDungActivity;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungAdapter extends BaseAdapter {

    Context context;
    ArrayList<NguoiDung> list;

    public NguoiDungAdapter(Context context, ArrayList<NguoiDung> list) {
        this.context = context;
        this.list = list;
    }

    public NguoiDungAdapter() {
    }

    public NguoiDungAdapter(ThemNguoiDungActivity context, List<NguoiDung> listNguoiDung) {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.loaisach_oneitem, null);
            holder.tv_tenNguoiDung = view.findViewById(R.id.tv_tenNguoiDung);
            holder.tv_nguoiDungPassword = view.findViewById(R.id.tv_nguoiDungPassword);
            holder.iv_delete = view.findViewById(R.id.iv_delete);

//            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        NguoiDung nguoiDung = list.get(i);
        holder.tv_tenNguoiDung.setText(nguoiDung.getHoTen().toString());
        holder.tv_nguoiDungPassword.setText(nguoiDung.getPassword().toString());

        return view;
    }

    class ViewHolder {
        TextView tv_tenNguoiDung, tv_nguoiDungPassword;
        ImageView iv_delete;
    }
}
