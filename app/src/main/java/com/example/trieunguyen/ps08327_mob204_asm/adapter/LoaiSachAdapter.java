package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.LoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.ThemLoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends BaseAdapter {

    Context context;
    ArrayList<LoaiSach> list;
    LoaiSachDAO loaiSachDAO;

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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.loaisach_oneitem, null);
            holder.tv_id = view.findViewById(R.id.tv_id);
            holder.tv_loaiSach = view.findViewById(R.id.tv_loaiSach);
            holder.iv_delete = view.findViewById(R.id.iv_delete);

            loaiSachDAO = new LoaiSachDAO(context);
            final LoaiSach loaiSach = list.get(i);

            //gán dữ liệu vào các biến để lưu lại thông tin của item bị xóa
            final String maCu = loaiSach.getMa();
            final String tenCu = loaiSach.getTen();
            final String moTaCu = loaiSach.getMota();
            final int viTriCu = loaiSach.getVitri();

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String loaiBiXoa = loaiSach.getTen();
                    final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                    confirmDialog.setTitle("Xác nhận");

                    confirmDialog.setMessage("Xóa loại sách " + loaiBiXoa.toUpperCase() +"?");

                    confirmDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                            loaiSachDAO.delete(list.get(i).getMa());
                            list.remove(i);
                            notifyDataSetChanged();
//                            Toast.makeText(context, "Đã xóa sách " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();

                            //hiện snackbar
                            Snackbar snackbar = Snackbar.make(viewGroup, "Đã xóa loại sách: " + loaiBiXoa.toUpperCase(), 4000);
                            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loaiSach.setMa(maCu);
                                    loaiSach.setTen(tenCu);
                                    loaiSach.setMota(moTaCu);
                                    loaiSach.setVitri(viTriCu);
                                    list.add(loaiSach);
                                    loaiSachDAO.insert(loaiSach);
                                    list = loaiSachDAO.getAll();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã hoàn tác xóa loại: " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            //SET MÀU CHỮ THÔNG BÁO
                            View sView = snackbar.getView();
                            TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
                            tv.setTextColor(Color.BLACK);
                            tv.setTextSize(16);
                            //SET MÀU BACKGROUND SNACKBAR
                            sView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbarBackground));
                            //SET MÀU CHỮ NÚT ACTION
                            snackbar.setActionTextColor(Color.RED);
                            snackbar.show();
                        }
                    });

                    confirmDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                            dialogInterface.cancel();
                        }
                    });
                    confirmDialog.show();
                }
            });

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
        ImageView iv_delete;
    }
}
