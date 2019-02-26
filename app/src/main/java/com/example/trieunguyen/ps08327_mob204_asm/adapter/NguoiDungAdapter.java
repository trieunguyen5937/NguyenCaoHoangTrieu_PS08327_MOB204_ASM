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
import com.example.trieunguyen.ps08327_mob204_asm.ThemNguoiDungActivity;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.NguoiDungDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungAdapter extends BaseAdapter {

    Context context;
    ArrayList<NguoiDung> list;
    NguoiDungDAO nguoiDungDAO;

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
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(R.layout.nguoidung_oneitem, null);
            holder.tv_username = view.findViewById(R.id.tv_username);
            holder.tv_password = view.findViewById(R.id.tv_password);
            holder.iv_delete = view.findViewById(R.id.iv_delete);

            nguoiDungDAO = new NguoiDungDAO(context);
            final NguoiDung nguoiDung = list.get(i);

            //gán dữ liệu vào các biến để lưu lại thông tin của item bị xóa
            final String unCu = nguoiDung.getUsername();
            final String pwCu = nguoiDung.getPassword();
            final String phoneCu = nguoiDung.getPhone();
            final String hoTenCu = nguoiDung.getHoTen();

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String userBiXoa = nguoiDung.getUsername();
                    final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                    confirmDialog.setTitle("Xác nhận");

                    confirmDialog.setMessage("Xóa user " + userBiXoa.toUpperCase() +"?");

                    confirmDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int a) {
                            nguoiDungDAO.delete(list.get(i).getUsername());
                            list.remove(i);
                            notifyDataSetChanged();
//                            Toast.makeText(context, "Đã xóa sách " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();

                            //hiện snackbar
                            Snackbar snackbar = Snackbar.make(viewGroup, "Đã xóa user: " + userBiXoa.toUpperCase(), 4000);
                            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    nguoiDung.setUsername(unCu);
                                    nguoiDung.setPassword(pwCu);
                                    nguoiDung.setPhone(phoneCu);
                                    nguoiDung.setHoTen(hoTenCu);
                                    list.add(nguoiDung);
                                    nguoiDungDAO.insert(nguoiDung);
                                    list = nguoiDungDAO.getAll();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Đã hoàn tác xóa user: " + userBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
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
        NguoiDung nguoiDung = list.get(i);
        holder.tv_username.setText(nguoiDung.getUsername());
        holder.tv_password.setText(nguoiDung.getPassword());

        return view;
    }

    class ViewHolder {
        TextView tv_username, tv_password;
        ImageView iv_delete;
    }
}
