package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.ThemLoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SachAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sach> list;
    SachDAO sachDAO;
    long VNĐ;

    public SachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    public SachAdapter() {
    }

    public SachAdapter(ThemLoaiSachActivity context, List<Sach> listSach) {
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
            view = inflater.inflate(R.layout.sach_oneitem, null);
            holder.tv_tenSach = view.findViewById(R.id.tv_tenSach);
            holder.tv_tacGia = view.findViewById(R.id.tv_tacGia);
            holder.tv_NXB = view.findViewById(R.id.tv_nxb);
            holder.tv_giaBia = view.findViewById(R.id.tv_giaBia);
            holder.iv_more = view.findViewById(R.id.iv_more);

            sachDAO = new SachDAO(context);
            final Sach sach = list.get(i);
//
//            //gán dữ liệu vào các biến để lưu lại thông tin của item bị xóa
//            final String maCu = loaiSach.getMa();
//            final String tenCu = loaiSach.getTen();
//            final String moTaCu = loaiSach.getMota();
//            final int viTriCu = loaiSach.getVitri();
//
//            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    final String loaiBiXoa = loaiSach.getTen();
//                    final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
//                    confirmDialog.setTitle("Xác nhận");
//
//                    confirmDialog.setMessage("Xóa sách " + loaiBiXoa.toUpperCase() +"?");
//
//                    confirmDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int a) {
//                            loaiSachDAO.delete(list.get(i).getMa());
//                            list.remove(i);
//                            notifyDataSetChanged();
////                            Toast.makeText(context, "Đã xóa sách " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
//
//                            //hiện snackbar
//                            Snackbar snackbar = Snackbar.make(viewGroup, "Đã xóa loại sách: " + loaiBiXoa.toUpperCase(), 4000);
//                            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    loaiSach.setMa(maCu);
//                                    loaiSach.setTen(tenCu);
//                                    loaiSach.setMota(moTaCu);
//                                    loaiSach.setVitri(viTriCu);
//                                    list.add(loaiSach);
//                                    loaiSachDAO.insert(loaiSach);
//                                    list = loaiSachDAO.getAll();
//                                    notifyDataSetChanged();
//                                    Toast.makeText(context, "Đã hoàn tác xóa loại: " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            //SET MÀU CHỮ THÔNG BÁO
//                            View sView = snackbar.getView();
//                            TextView tv = sView.findViewById(android.support.design.R.id.snackbar_text);
//                            tv.setTextColor(Color.BLACK);
//                            tv.setTextSize(16);
//                            //SET MÀU BACKGROUND SNACKBAR
//                            sView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbarBackground));
//                            //SET MÀU CHỮ NÚT ACTION
//                            snackbar.setActionTextColor(Color.RED);
//                            snackbar.show();
//                        }
//                    });
//
//                    confirmDialog.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int a) {
//                            dialogInterface.cancel();
//                        }
//                    });
//                    confirmDialog.show();
//                }
//            });

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        Sach sach = list.get(i);

        //Định dạng kiểu tiền tệ.
        VNĐ = sach.getGiaBia();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str = currencyVN.format(VNĐ);

        holder.tv_tenSach.setText(sach.getTenSach().toString());
        holder.tv_tacGia.setText(sach.getTacGia().toString());
        holder.tv_NXB.setText(sach.getNXB().toString());
        holder.tv_giaBia.setText(str);

        return view;
    }

    class ViewHolder {
        TextView tv_tenSach, tv_NXB, tv_tacGia, tv_giaBia;
        ImageView iv_more;
    }
}
