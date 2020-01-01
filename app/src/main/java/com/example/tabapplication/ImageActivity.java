package com.example.tabapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.tabapplication.adapters.ViewpageAdapter;


public class ImageActivity extends Activity {

    ViewpageAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row);
        Intent receivedIntent = getIntent();

        //----------------------------------------------------------------
        // 확대되는 이미지를 보여주기 위해 ImageView 뷰를 설정합니다.
        ImageView imageView = findViewById(R.id.imageView);
        TextView textView = findViewById(R.id.textView);
        int imageID = (Integer)receivedIntent.getExtras().get("image ID");
        String filename = (String)receivedIntent.getExtras().get("file name");

        viewPager = findViewById(R.id.view);
        adapter = new ViewpageAdapter(this, imageView, textView, imageID, filename);
        viewPager.setAdapter(adapter);
    }
    private void setImage(ImageView imageView) {
        //----------------------------------------------------------------
        // 초기 액티비티의 GridView 뷰의 이미지 항목을 클릭할 때 생성된 인텐트는
        // 이 액티비티는 getIntent 메소드를 호출하여 접근할 수 있습니다.
        Intent receivedIntent = getIntent();

        //----------------------------------------------------------------
        // 확대되는 이미지의 리소스 ID를 인텐트로부터 읽어들이고,
        // 그것을 ImageView 뷰의 이미지 리소스로 설정합니다.
        int imageID = (Integer)receivedIntent.getExtras().get("image ID");

        imageView.setImageResource(imageID);
    }
    private void setText(TextView textView) {
        //----------------------------------------------------------------
        // 초기 액티비티의 GridView 뷰의 이미지 항목을 클릭할 때 생성된 인텐트는
        // 이 액티비티는 getIntent 메소드를 호출하여 접근할 수 있습니다.
        Intent receivedIntent = getIntent();

        //----------------------------------------------------------------
        // 확대되는 이미지의 리소스 ID를 인텐트로부터 읽어들이고,
        // 그것을 ImageView 뷰의 이미지 리소스로 설정합니다.

        String filename = (String)receivedIntent.getExtras().get("file name");
        textView.setText(filename);
    }
}