package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.ThemLoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends BaseAdapter {

    Context context;
    ArrayList<LoaiSach> list;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
    }

    public LoaiSachAdapter() {
    }

    public LoaiSachAdapter(ThemLoaiSachActivity context, List<LoaiSach> listLoaiSach) {
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
            holder.tv_id = view.findViewById(R.id.tv_id);
            holder.tv_loaiSach = view.findViewById(R.id.tv_loaiSach);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        LoaiSach loaiSach = list.get(i);
        holder.tv_id.setText(loaiSach.getMa().toString());
        holder.tv_loaiSach.setText(loaiSach.getTen().toString());

        return view;
    }

    class ViewHolder {
        TextView tv_id, tv_loaiSach;
    }
}
