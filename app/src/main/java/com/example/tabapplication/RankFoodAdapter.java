package com.example.tabapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tabapplication.models.Foodcount;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RankFoodAdapter extends RecyclerView.Adapter<RankFoodAdapter.ViewHolder> {
    private ArrayList<Foodcount> listData = new ArrayList<>();



    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView food;
        TextView count;
        public ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            food = itemView.findViewById(R.id.tv_foodname);
            count = itemView.findViewById(R.id.tv_count);
        }

        void onBind(Foodcount data) {
            food.setText(data.getfoodname());
            count.setText(data.getcount());
        }
    }


    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public RankFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.rank, parent, false) ;
        RankFoodAdapter.ViewHolder vh = new RankFoodAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull RankFoodAdapter.ViewHolder holder, int position) {
        holder.onBind(listData.get(position));
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return listData.size();
    }


    void addItem(Foodcount data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

}



