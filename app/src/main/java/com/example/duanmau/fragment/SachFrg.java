package com.example.duanmau.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.dao.SachDao;
import com.example.duanmau.dao.TheLoaiDao;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.TheLoai;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class SachFrg extends Fragment {
    RecyclerView rcv;
    FloatingActionButton fltaddsach;
    SachDao dao;
    SachAdapter adapter;
    ArrayList<Sach> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.sach_frg,container,false);
        rcv = view.findViewById(R.id.rcvsach);
        fltaddsach  =view.findViewById(R.id.fltaddsach);
        dao = new SachDao(getContext());
        list = dao.getSachAll();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new SachAdapter(list,getContext());
        rcv.setAdapter(adapter);
        fltaddsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSach();
            }
        });
        return view;
    }
    public void addSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText txttens = view.findViewById(R.id.ed_addTenS);

        TextInputEditText txtnxb = view.findViewById(R.id.ed_addNamXB);

        TextInputEditText txtgiathue = view.findViewById(R.id.ed_addGiaThue);
        Spinner spnSach = view.findViewById(R.id.spnSach);

        Button btnadds = view.findViewById(R.id.S_add);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);

        btnadds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txttens.getText().toString();
                String nxb = txtnxb.getText().toString();
                String giathue = txtgiathue.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                int tien = Integer.parseInt((giathue));
                int maloai = (int) hs.get("maLoai");
                int sx = Integer.parseInt((nxb));
                boolean check = dao.insert(ten,tien,maloai,sx);
                if (check){
                    loadata();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "tb", Toast.LENGTH_SHORT).show();
                }


            }
        });







    }
    private ArrayList<HashMap<String , Object>> getDSLoaiSach(){
        TheLoaiDao loaisach = new TheLoaiDao(getContext());
        ArrayList<TheLoai> list1 = loaisach.getalltheloai();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (TheLoai ls : list1){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", ls.getMaLoai());
            hs.put("tenLoai", ls.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }
    private void loadata(){
        list.clear();
        list.addAll(dao.getSachAll());
        adapter.notifyDataSetChanged();
    }
}