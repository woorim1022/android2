package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public int inSampleSize = 1;


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    private static String basePath;

    public float imageViewRotation = 90;
    public String TAG = "Camera Example :: ";

    private Button takePicBtn;
    private ImageView resultView;
    private TextView imgPath;
    private Gallery customGallery;
    private CustomGalleryAdapter customGalAdapter;

    private String[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1) ;
        tabHost1.setup() ;

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"tab1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.tab1) ;
        ts1.setIndicator("TAB 1") ;
        tabHost1.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"tab2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.tab2) ;
        ts2.setIndicator("TAB 2") ;
        tabHost1.addTab(ts2) ;

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"tab3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3") ;
        ts3.setContent(R.id.tab3) ;
        ts3.setIndicator("TAB 3") ;
        tabHost1.addTab(ts3) ;

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        //SimpleTextAdapter adapter = new SimpleTextAdapter(list) ;
        //recyclerView.setAdapter(adapter) ;













        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
//                return null;
            }
        }

        basePath = mediaStorageDir.getPath();

        imgPath = (TextView)findViewById(R.id.imgpath);
        resultView = (ImageView)findViewById(R.id.resultview);
        takePicBtn = (Button)findViewById(R.id.takepicbtn);
        // Button click시, Camera Intent를 불러 옴
        takePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }


        });
        File file = new File(basePath);
        imgs = file.list();
        for(int i=0; i<imgs.length; i++){
            imgPath.setText(imgs[i]);
        }

        customGallery = (Gallery)findViewById(R.id.customgallery); // activity_main.xml에서 선언한 Gallery를 연결
        customGalAdapter = new CustomGalleryAdapter(getApplicationContext(), basePath); // 위 Gallery에 대한 Adapter를 선언
        customGallery.setAdapter(customGalAdapter); // Gallery에 위 Adapter를 연결
        // Gallery의 Item을 Click할 경우 ImageView에 보여주도록 함
        customGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap bm = BitmapFactory.decodeFile(basePath+ File.separator +imgs[position]);
                Bitmap bm2 = ThumbnailUtils.extractThumbnail(bm, bm.getWidth() / inSampleSize, bm.getHeight() / inSampleSize);
                resultView.setImageBitmap(bm2);
                imgPath.setText(basePath+File.separator+imgs[position]);
            }
        });
        // Gallery의 Item을 LongClick할 경우 해당 File을 삭제하도록 함.
        // 이 부분에서 동작은 하나, 삭제 후 결과가 View에 반영이 안되어 추가 보완 필요
        customGallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                File temp = new File(basePath+File.separator+imgs[position]);
                temp.delete();
                return false;
            }
        });


    }
}

