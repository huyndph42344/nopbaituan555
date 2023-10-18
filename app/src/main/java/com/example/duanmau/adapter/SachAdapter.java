package com.example.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.model.Sach;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHoler> {
    private ArrayList<Sach> list;
    private Context context;

    SachDao dao;

    public SachAdapter(ArrayList<Sach> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new SachDao(context);
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.txtmas.setText(String.valueOf(list.get(position).getMaSach()));
        holder.txttens.setText((list.get(position).getTenSach()));


        holder.txtnamsb.setText(String.valueOf(list.get(position).getNamSuatBan()));
        holder.txtgiat.setText(String.valueOf(list.get(position).getGiaThue()));
        holder.txtloais.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.txttenloai.setText((list.get(position).getTenLoai()));
        Sach s=list.get(position);
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = dao.delete(s.getMaSach());
                switch (result) {
                    case 1:
                        list.clear();
                        list.addAll(dao.getSachAll());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                }

            }
        });
        holder.btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.edit_sach, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();

                TextInputLayout in_TenSach = view.findViewById(R.id.in_updateTenS);
                TextInputLayout in_namSB = view.findViewById(R.id.in_updateNamSB);
                TextInputLayout in_GiaThue = view.findViewById(R.id.in_updateGiaThue);
                TextInputEditText ed_TenSach = view.findViewById(R.id.ed_updateTenS);
                TextInputEditText ed_namSB = view.findViewById(R.id.ed_updateNamSB);
                TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_updateGiaThue);
                Spinner spnSach = view.findViewById(R.id.spnSach);
                Button btnupdate = view.findViewById(R.id.S_update);
                Button btncancel = view.findViewById(R.id.S_Cancel);


                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String newTenSach = ed_TenSach.getText().toString();
                        int newNamSB = Integer.parseInt(ed_namSB.getText().toString());
                        int newGiaThue = Integer.parseInt(ed_GiaThue.getText().toString());
                        String selectedItem = spnSach.getSelectedItem().toString(); // Đọc giá trị từ Spinner

                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHoler extends RecyclerView.ViewHolder{

        TextView txtmas,txttens,txtnamsb,txtgiat,txtloais,txttenloai;
        Button btnxoa,btnsua;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtmas = itemView.findViewById(R.id.txtmasach1);
            txttens = itemView.findViewById(R.id.txttensach1);
            txtnamsb= itemView.findViewById(R.id.txtnamSuatBan);
            txtgiat = itemView.findViewById(R.id.txtgiathue1);
            txtloais = itemView.findViewById(R.id.txtloaisach1);
            txttenloai = itemView.findViewById(R.id.txttenloaisach1);
            btnxoa = itemView.findViewById(R.id.btnxoa);
            btnsua = itemView.findViewById(R.id.btnsua);

        }
    }
}
