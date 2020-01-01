package com.example.tabapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.tabapplication.R;

public class ViewpageAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    private  Context context;
    private ImageView imageView;
    private TextView textView;
    int imageID;
    String fileName;
    private int[] imageIDs = new int[] { R.drawable.ball, R.drawable.download, R.drawable.flower, R.drawable.leaf, R.drawable.sky, R.drawable.snowman, R.drawable.apple, R.drawable.bonobono, R.drawable.bubble, R.drawable.flag, R.drawable.frog, R.drawable.frozen, R.drawable.mickey, R.drawable.mouse2020, R.drawable.pororo, R.drawable.ryan, R.drawable.shoe, R.drawable.totoro, R.drawable.tulip, R.drawable.whale,};
    private String[] fileNames = new String[]{"ball.jpg", "download.jpg", "flower.jpg", "leaf.jpg", "sky.jpg", "snowman.jpg", "apple.jpg", "bonobono.jpg", "bubble.jpg", "flag.jpg", "frog.jpg", "frozen.jpg", "mickey.png", "mouse2020.jpg", "pororo.jpg", "ryan.png", "shoe.jpg", "totoro.jpg", "tulip.jpg", "whale.jpg"};

    public ViewpageAdapter(Context context, ImageView imageView1, TextView textView1, int imageID1, String filename1){
        this.context = context;
        this.imageView = imageView1;
        this.textView = textView1;
        this.imageID = imageID1;
        this.fileName = filename1;
    }
    @Override
    public int getCount() {
        return imageIDs.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout)object);
    }
    @Override
    public  Object instantiateItem(ViewGroup container, int position) {


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.page, container, false);
        ImageView imageView = v.findViewById(R.id.imageView);
        TextView textView = v.findViewById(R.id.textView);
        int num = 0;
        for(int i = 0; i < fileNames.length; i++) {
            if (fileName.equals(fileNames[i])) {
                num = i;
            }
        }
        if(position + num <=19) {
            imageView.setImageResource(imageIDs[position + num]);
            textView.setText(fileNames[position + num]);
        }
        else{
            imageView.setImageResource(imageIDs[position-1]);
            textView.setText(fileNames[position-1]);
        }
        container.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }


}
