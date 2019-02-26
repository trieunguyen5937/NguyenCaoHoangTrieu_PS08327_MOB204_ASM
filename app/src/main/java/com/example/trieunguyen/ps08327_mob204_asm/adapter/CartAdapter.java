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
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDonChiTiet;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    ArrayList<HoaDonChiTiet> arrHoaDonChiTiet;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public CartAdapter(Activity context, ArrayList<HoaDonChiTiet> arrayHoaDonChiTiet) {
        super();
        this.context = context;
        this.arrHoaDonChiTiet = arrayHoaDonChiTiet;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @Override
    public int getCount() {
        return arrHoaDonChiTiet.size();
    }

    @Override
    public Object getItem(int position) {
        return arrHoaDonChiTiet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cart_oneitem, null);
            holder.tv_maSach = (TextView) convertView.findViewById(R.id.tv_maSach);
            holder.tv_soLuong = (TextView) convertView.findViewById(R.id.tv_soLuong);
            holder.tv_giaBia = (TextView) convertView.findViewById(R.id.tv_giaBia);
            holder.tv_thanhTien = (TextView) convertView.findViewById(R.id.tv_thanhTien);
            holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hoaDonChiTietDAO.deleteHoaDonChiTietByID(String.valueOf(arrHoaDonChiTiet.get(position).getMaHDCT()));
                    arrHoaDonChiTiet.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        HoaDonChiTiet _entry = (HoaDonChiTiet) arrHoaDonChiTiet.get(position);
        holder.tv_maSach.setText("Mã sách: " + _entry.getSach().getMaSach());
        holder.tv_soLuong.setText("Số lượng: " + _entry.getSoLuongMua());
        holder.tv_giaBia.setText("Giá bìa: " + _entry.getSach().getGiaBia() + " VND");
        holder.tv_thanhTien.setText("Thành tiền: " + _entry.getSoLuongMua() * _entry.getSach().getGiaBia() + " VND");
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_maSach;
        TextView tv_soLuong;
        TextView tv_giaBia;
        TextView tv_thanhTien;
        ImageView iv_delete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(ArrayList<HoaDonChiTiet> items) {
        this.arrHoaDonChiTiet = items;
        notifyDataSetChanged();
    }
}
