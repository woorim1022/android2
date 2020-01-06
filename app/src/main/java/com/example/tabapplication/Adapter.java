package com.example.tabapplication;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Dictionary;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Person> listData = new ArrayList<>();




    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name ;
        TextView tel;
            public ViewHolder(View itemView) {
                super(itemView) ;

                // 뷰 객체에 대한 참조. (hold strong reference)
                name = itemView.findViewById(R.id.name_id) ;
                tel = itemView.findViewById(R.id.tel_id) ;
            }
            void onBind(Person data) {
                name.setText(data.getName());
                tel.setText(data.getTel());
            }
    }




    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Adapter.ViewHolder(view);
    }



    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }



    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return listData.size() ;
    }




    void addItem(Person data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }




}