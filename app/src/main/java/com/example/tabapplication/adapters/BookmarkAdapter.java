package com.example.tabapplication.adapters;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.tabapplication.ActivityformapAPI;
import com.example.tabapplication.ImageActivity;
import com.example.tabapplication.R;
import com.example.tabapplication.models.Bookmark;

import java.util.ArrayList;

public class BookmarkAdapter extends BaseAdapter {


    private ArrayList<Bookmark> bookmarks;

    Context context = null;
    int[] imageIDs = null;
    String[] filenames = null;
    ArrayList<Bookmark> mList;

    public BookmarkAdapter(Context context, int[] imageIDs, String[] filenames) {
        this.context = context;
        this.imageIDs = imageIDs;
        this.filenames = filenames;
        bookmarks = new ArrayList<>();
    }

    //중복등록 방지 메소드, 북마크 목록에서 동일한 것이 발견되면 true 를 반환하는 메소드
    public boolean hasDuplicate(Bookmark bookmark1, ArrayList<Bookmark> List) {
        Bookmark bookmark = bookmark1;
        mList = List;
        boolean isDuplicate = false;
        for(int i = 0; i < mList.size();i++) {
            Bookmark current = mList.get(i);
            if ( current.getAddres() == bookmark.getAddres()) {
                isDuplicate = true;
            }
        }
        return isDuplicate;
    }

    //ArrayList인 bookmarks에 즐겨찾기를 하나씩 추가하는 메소드, bookmark 객체를 받아서 bookmark 에 그대로 추가
    public void addBookmark(Bookmark bookmark) {
        bookmarks.add(bookmark);
        notifyDataSetChanged();          //자료가 바뀌면 바뀌는 대로 리스트를 업데이트 하는 기능, 북마크를 추가하면 addBookmark가 실행되고 notify~() 가 실행됨에 따라 새로운 북마크가 리스트에 보임
    }



    @Override
    public int getCount() {
        return bookmarks.size();
    }

    @Override
    public Object getItem(int position) {
        return bookmarks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();
        final Bookmark bookmark = bookmarks.get(position);

        //convertView가 없다면 item_bookmark를 inflate
        if ( convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_bookmark, parent, false);  //boookmark 한개

        }

        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        tv_title.setText(bookmark.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageActivity.class);//new Intent(현재 액티비티, 이동할 액티비티 context : 그 당신의 액티비티 또는 환경
                    for(int i = 0; i < imageIDs.length; i++){
                        if(filenames[i].equals(bookmark.getFile())) {
                            intent.putExtra("image ID", imageIDs[i]);
                            intent.putExtra("file name", filenames[i]);
                            context.startActivity(intent);
                        }
                    }

            }
        });

        return convertView;
    }
}