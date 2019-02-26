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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.ThemLoaiSachActivity;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonChiTietDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.HoaDonDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.HoaDon;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<HoaDon> list;
    ArrayList<HoaDon> listFull;
    public LayoutInflater inflater;
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public HoaDonAdapter() {
    }

    public HoaDonAdapter(Context context, ArrayList<HoaDon> list) {
        super();
        this.context = context;
        this.list = list;
        this.listFull = listFull;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        hoaDonDAO = new HoaDonDAO(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
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
            view = inflater.inflate(R.layout.hoadon_oneitem, null);
            holder.tv_maHoaDon = view.findViewById(R.id.tv_maHoaDon);
            holder.tv_ngay = view.findViewById(R.id.tv_ngay);
            holder.iv_delete = view.findViewById(R.id.iv_delete);

            hoaDonDAO = new HoaDonDAO(context);
            final HoaDon hoaDon = list.get(i);

            //gán dữ liệu vào các biến để lưu lại thông tin của item bị xóa
            final String maHoaDonCu = hoaDon.getMaHoaDon();
            final Date ngayCu = hoaDon.getNgayMua();

            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hoaDonChiTietDAO.checkHoaDon(list.get(i).getMaHoaDon())) {
                        Toast.makeText(context, "Bạn phải xoá hoá đơn chi tiết trước khi xoá hoá đơn này", Toast.LENGTH_LONG).show();
                    } else {
                        final String hdBiXoa = hoaDon.getMaHoaDon();
                        final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                        confirmDialog.setTitle("Xác nhận");

                        confirmDialog.setMessage("Xóa hóa đơn " + hdBiXoa.toUpperCase() + "?");

                        confirmDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int a) {
                                hoaDonDAO.deleteHoaDonByID(list.get(i).getMaHoaDon());
                                list.remove(i);
                                notifyDataSetChanged();
//                            Toast.makeText(context, "Đã xóa sách " + loaiBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();

                                //hiện snackbar
                                Snackbar snackbar = Snackbar.make(viewGroup, "Đã xóa hóa đơn: " + hdBiXoa.toUpperCase(), 4000);
                                snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        hoaDon.setMaHoaDon(maHoaDonCu);
                                        hoaDon.setNgayMua(ngayCu);
                                        list.add(hoaDon);
                                        hoaDonDAO.insert(hoaDon);
//                                    list = hoaDonDAO.getAllHoaDon();
                                        notifyDataSetChanged();
                                        Toast.makeText(context, "Đã hoàn tác xóa hóa đơn: " + hdBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
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
                }
            });

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        HoaDon hoaDon = list.get(i);
        holder.tv_maHoaDon.setText(hoaDon.getMaHoaDon().toString());
        holder.tv_ngay.setText(sdf.format(hoaDon.getNgayMua()));
//        holder.tv_ngay.setText(hoaDon.getNgayMua().toString());

        return view;
    }

    class ViewHolder {
        TextView tv_maHoaDon, tv_ngay;
        ImageView iv_delete;
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    //tham khảo: https://codinginflow.com/tutorials/android/searchview-recyclerview
    //và https://www.androidhive.info/2017/11/android-recyclerview-with-search-filter-functionality/
    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<HoaDon> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listFull);
            } else {


                for (HoaDon hdItem : listFull) {
                    String charString = charSequence.toString().toLowerCase();
                    if (hdItem.getMaHoaDon().toLowerCase().contains(charString)) {
                        filteredList.add(hdItem);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list = (ArrayList<HoaDon>) filterResults.values;
            notifyDataSetChanged();
        }
    };
}
