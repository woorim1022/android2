package com.example.tabapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageGridAdapter extends BaseAdapter {



    Context context;
    int[] imageIDs;
    String[] filenames;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();





    public ImageGridAdapter(Context context, int[] imageIDs, String[] filenames) {
        this.context = context;
        this.imageIDs = imageIDs;
        this.filenames = filenames;
    }





    public int getCount() {
        return (null != imageIDs) ? imageIDs.length:0;
    }





    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position]:0;
    }





    public long getItemId(int position) {
        return position;
    }





    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if ( null != convertView)
            imageView = (ImageView)convertView;
        else {
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의합니다.
            // 그리고 그것의 크기를 320*240으로 줄입니다.
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            bitmaps.add(bmp);
            imageView.setImageBitmap(bitmaps.get(position));
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰들을 정의합니다.
            // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체입니다.
            //---------------------------------------------------------------
            // 사진 항목들의 클릭을 처리하는 ImageClickListener 객체를 정의합니다.
            // 그리고 그것을 ImageView의 클릭 리스너로 설정합니다.
            ImageClickListener imageViewClickListener = new ImageClickListener(context, imageIDs[position],filenames[position]);
            imageView.setOnClickListener(imageViewClickListener);
        }
        return imageView;
    }
    public void addImage(Bitmap bmp) {
        bitmaps.add(bmp);
        notifyDataSetChanged();
    }






}
