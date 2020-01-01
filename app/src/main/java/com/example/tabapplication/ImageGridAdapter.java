package com.example.tabapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {


    Context context = null;

    int[] imageIDs = null;
    String[] filenames = null;
    Bitmap imageID = null;
    String filename = null;

    public ImageGridAdapter(Context context, int[] imageIDs, String[] filenames, Bitmap imageID, String filename) {
        this.context = context;
        this.imageIDs = imageIDs;
        this.filenames = filenames;
        this.imageID = imageID;
        this.filename = filename;
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
        ImageView imageView = null;

        if ( null != convertView)
            imageView = (ImageView)convertView;
        else {
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰의 비트맵을 정의합니다.
            // 그리고 그것의 크기를 320*240으로 줄입니다.
            // 크기를 줄이는 이유는 메모리 부족 문제를 막을 수 있기 때문입니다.
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);
            imageView = new ImageView(context);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(bmp);
            //---------------------------------------------------------------
            // GridView 뷰를 구성할 ImageView 뷰들을 정의합니다.
            // 뷰에 지정할 이미지는 앞에서 정의한 비트맵 객체입니다.
            if (filename == null){
                imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(bmp);

            }
            else {
                imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(imageID);

            }

            //---------------------------------------------------------------
            // 사진 항목들의 클릭을 처리하는 ImageClickListener 객체를 정의합니다.
            // 그리고 그것을 ImageView의 클릭 리스너로 설정합니다.
            ImageClickListener imageViewClickListener = new ImageClickListener(context, imageIDs[position],filenames[position]);
            imageView.setOnClickListener(imageViewClickListener);
        }
        return imageView;
    }
}