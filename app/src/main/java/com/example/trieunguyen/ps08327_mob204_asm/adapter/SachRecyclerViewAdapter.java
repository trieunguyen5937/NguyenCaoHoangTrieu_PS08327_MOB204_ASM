package com.example.trieunguyen.ps08327_mob204_asm.adapter;

import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trieunguyen.ps08327_mob204_asm.R;
import com.example.trieunguyen.ps08327_mob204_asm.dao.LoaiSachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.dao.SachDAO;
import com.example.trieunguyen.ps08327_mob204_asm.model.LoaiSach;
import com.example.trieunguyen.ps08327_mob204_asm.model.Sach;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class SachRecyclerViewAdapter extends RecyclerView.Adapter<SachRecyclerViewAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Sach> listSach;
    ArrayList<Sach> listSachFull;
    ArrayList<LoaiSach> listLoaiSach = new ArrayList<>();
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    long VNĐ;
    EditText et_maSach, et_tacGia, et_NXB, et_giaBia, et_soLuong;
    Spinner sp_tenLoaiSach;
    Boolean toggleEdit = false;

    public SachRecyclerViewAdapter(Context context, ArrayList<Sach> listSach) {
        this.context = context;
        this.listSach = listSach;
        this.listSachFull = new ArrayList<>(listSach);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sach_oneitem, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenSach, tv_NXB, tv_tacGia, tv_giaBia;
        ImageView iv_more;
        CardView parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            tv_tenSach = itemView.findViewById(R.id.tv_tenSach);
            tv_tacGia = itemView.findViewById(R.id.tv_tacGia);
            tv_NXB = itemView.findViewById(R.id.tv_nxb);
            tv_giaBia = itemView.findViewById(R.id.tv_giaBia);
            iv_more = itemView.findViewById(R.id.iv_more);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        sachDAO = new SachDAO(context);
        final Sach sach = listSach.get(position);

        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = loaiSachDAO.getAll();

        String tenSach = sach.getTenSach();
        String tacGia = "Tác giả: " + sach.getTacGia();
        String nxb = "Nhà xuất bản: " + sach.getNXB();
        //Định dạng kiểu tiền tệ.
        VNĐ = sach.getGiaBia();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String gia = currencyVN.format(VNĐ);

        //SET GIÁ TRỊ VÀ SHOW LÊN LIST ITEM
        holder.tv_tenSach.setText(tenSach);
        holder.tv_tacGia.setText(tacGia);
        holder.tv_NXB.setText(nxb);
        holder.tv_giaBia.setText(gia);

        //gán dữ liệu vào các biến để lưu lại thông tin của item bị xóa, sửa
        final String maSachCu = sach.getMaSach();
        final String tenLoaiSachCu = sach.getTenLoaiSach();
        final String tenCu = sach.getTenSach();
        final String tacGiaCu = sach.getTacGia();
        final String nxbCu = sach.getNXB();
        final int giaCu = sach.getGiaBia();
        final int soLuongCu = sach.getSoLuong();

        //tham khảo: https://www.simplifiedcoding.net/create-options-menu-recyclerview-item-tutorial/
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(final View view) {
                //ẨN KEYBOARD KHI NHẤN VÀO ICON MORE
                InputMethodManager imm = (InputMethodManager) view.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //TẠO POPUP MENU
                PopupMenu popupMenu = new PopupMenu(context, holder.iv_more);
                //HIỆN ICON TRONG MENU, tham khảo: https://readyandroid.wordpress.com/popup-menu-with-icon/
                try {
                    Field[] fields = popupMenu.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if ("mPopup".equals(field.getName())) {
                            field.setAccessible(true);
                            Object menuPopupHelper = field.get(popupMenu);
                            Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                            Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                            setForceIcons.invoke(menuPopupHelper, true);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //GÁN LAYOUT CHO MENU
                popupMenu.inflate(R.menu.context_menu_sach);
                //TẠO SỰ KIỆN
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.chiTiet:
                                //hàm xem, sửa
                                final AlertDialog.Builder updateDialog = new android.support.v7.app.AlertDialog.Builder(context);
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View v = inflater.inflate(R.layout.update_sach_dialog, null);
                                updateDialog.setView(v);
                                final AlertDialog myDialog = updateDialog.create();
                                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                final Button bt_update = v.findViewById(R.id.bt_update);
                                Button bt_cancel = v.findViewById(R.id.bt_cancel);
                                final TextView tv_edit = v.findViewById(R.id.tv_edit);

                                Sach mSach = listSach.get(position);

                                // *** mã sách là khóa chính nên không được edit ***
                                et_maSach = v.findViewById(R.id.et_maSach);
                                et_maSach.setText(sach.getMaSach());

                                sp_tenLoaiSach = v.findViewById(R.id.sp_tenLoaiSach);
                                SachSpinnerAdapter sachSpinnerAdapter = new SachSpinnerAdapter(context, listLoaiSach);
                                sp_tenLoaiSach.setAdapter(sachSpinnerAdapter);
                                sp_tenLoaiSach.setEnabled(false);
//                                String tenLoai = loaiSach.getTen();
//                                if (tenLoai!= null){
//                                    int spinnerIndex = sp_tenLoaiSach.getSelectedItemPosition();
//
//                                }

                                et_tacGia = v.findViewById(R.id.et_tacGia);
                                et_tacGia.setText(sach.getTacGia());

                                et_NXB = v.findViewById(R.id.et_NXB);
                                et_NXB.setText(sach.getNXB());

                                et_giaBia = v.findViewById(R.id.et_giaBia);
                                et_giaBia.setText(String.valueOf(sach.getGiaBia()));

                                et_soLuong = v.findViewById(R.id.et_soLuong);
                                et_soLuong.setText(String.valueOf(sach.getSoLuong()));

                                //set toggle nút sửa trong dialog
                                tv_edit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (toggleEdit == false) {
                                            sp_tenLoaiSach.setEnabled(true);
                                            et_tacGia.setEnabled(true);
                                            et_NXB.setEnabled(true);
                                            et_giaBia.setEnabled(true);
                                            et_soLuong.setEnabled(true);
                                            bt_update.setEnabled(true);
                                            bt_update.setTextColor(Color.BLACK);
                                            tv_edit.setText("Xong");
                                            toggleEdit = true;
                                        } else {
                                            sp_tenLoaiSach.setEnabled(false);
                                            et_tacGia.setEnabled(false);
                                            et_NXB.setEnabled(false);
                                            et_giaBia.setEnabled(false);
                                            et_soLuong.setEnabled(false);
                                            bt_update.setEnabled(false);
                                            bt_update.setTextColor(Color.GRAY);
                                            tv_edit.setText("Sửa");
                                            toggleEdit = false;
                                        }
                                    }
                                });

                                bt_update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String tacGiaMoi = et_tacGia.getText().toString();
                                        String nxbMoi = et_NXB.getText().toString();
                                        String giaBiaMoi = et_giaBia.getText().toString();
                                        String soLuongMoi = et_soLuong.getText().toString();

                                        if (validForm(tacGiaMoi, nxbMoi, giaBiaMoi, soLuongMoi)) {
                                            int giabiamoi = Integer.parseInt(giaBiaMoi);
                                            int soluongmoi = Integer.parseInt(soLuongMoi);

                                            sach.setMaSach(et_maSach.getText().toString());

                                            int index = (int) sp_tenLoaiSach.getSelectedItemId();
                                            final LoaiSach loaiSach = listLoaiSach.get(index);
                                            loaiSach.setTen(loaiSach.getTen());

                                            sach.setTacGia(tacGiaMoi);
                                            sach.setNXB(nxbMoi);
                                            sach.setGiaBia(giabiamoi);
                                            sach.setSoLuong(soluongmoi);

                                            final SachDAO sachDAO = new SachDAO(context);
                                            sachDAO.update(sach);
                                            listSach = sachDAO.getAll();
                                            notifyDataSetChanged();
                                            myDialog.dismiss();

                                            Snackbar snackbar = Snackbar.make(holder.itemView, "Đã cập nhật sách", 4000);
                                            snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    sach.setMaSach(maSachCu);
                                                    sach.setTenLoaiSach(tenLoaiSachCu);
                                                    sach.setTacGia(tacGiaCu);
                                                    sach.setNXB(nxbCu);
                                                    sach.setGiaBia(giaCu);
                                                    sach.setSoLuong(soLuongCu);
                                                    sachDAO.update(sach);
                                                    listSach = sachDAO.getAll();
                                                    notifyDataSetChanged();
                                                    Toast.makeText(context, "Đã hoàn tác cập nhật", Toast.LENGTH_SHORT).show();
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
                                    }
                                });

                                bt_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        myDialog.dismiss();
                                    }
                                });

                                myDialog.show();

                                break;
                            case R.id.xoa:
                                //hàm xóa
                                final String sachBiXoa = sach.getTenSach();
                                final AlertDialog.Builder confirmDialog = new AlertDialog.Builder(context);
                                confirmDialog.setTitle("Xác nhận");
                                confirmDialog.setMessage("Xóa sách " + sachBiXoa.toUpperCase() + "?");

                                confirmDialog.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int a) {
                                        sachDAO.delete(listSach.get(position).getMaSach());
                                        listSach.remove(position);
                                        notifyDataSetChanged();

                                        //hiện snackbar, hoàn tác xóa
                                        Snackbar snackbar = Snackbar.make(view, "Đã xóa sách: " + sachBiXoa.toUpperCase(), 4000);
                                        snackbar.setAction("Hoàn tác", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                sach.setMaSach(maSachCu);
                                                sach.setTenLoaiSach(tenLoaiSachCu);
                                                sach.setTacGia(tacGiaCu);
                                                sach.setNXB(nxbCu);
                                                sach.setGiaBia(giaCu);
                                                sach.setSoLuong(soLuongCu);
                                                listSach.add(sach);
                                                sachDAO.insert(sach);
                                                listSach = sachDAO.getAll();
                                                notifyDataSetChanged();
                                                Toast.makeText(context, "Đã hoàn tác xóa loại: " + sachBiXoa.toUpperCase(), Toast.LENGTH_SHORT).show();
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

                                break;
                        }
                        return false;
                    }
                });
                //VỊ TRÍ CỦA POPUP MENU
                popupMenu.setGravity(Gravity.END);
                //HIỂN THỊ POPUP
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSach.size();
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

            ArrayList<Sach> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listSachFull);
            } else {
                for (Sach sachItem : listSachFull) {
                    String charString = charSequence.toString().toLowerCase();
                    if (sachItem.getTenSach().toLowerCase().contains(charString) || sachItem.getTenSach().toLowerCase().contains(charString)) {
                        filteredList.add(sachItem);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listSach = (ArrayList<Sach>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    private boolean validForm(String tacGia, String nxb, String giaBia, String soLuong) {
        try {
            if (tacGia.trim().length() == 0) {
                et_tacGia.requestFocus();
                throw new Exception("Vui lòng nhập tác giả");
            }
            if (nxb.trim().length() == 0) {
                et_NXB.requestFocus();
                throw new Exception("Vui lòng nhập NXB");
            }
            if (giaBia.trim().length() == 0) {
                et_giaBia.requestFocus();
                throw new Exception("Vui lòng nhập giá bìa");
            }
            int giabia = Integer.parseInt(giaBia.trim());
            if (giabia < 0) {
                et_giaBia.requestFocus();
                throw new Exception("Giá bìa không được là số âm");
            }
            if (soLuong.trim().length() == 0) {
                et_soLuong.requestFocus();
                throw new Exception("Vui lòng nhập số lượng");
            }
            int soluong = Integer.parseInt(soLuong.trim());
            if (soluong < 0) {
                et_soLuong.requestFocus();
                throw new Exception("Số lượng không được là số âm");
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Giá bìa và số lượng phải là số", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
