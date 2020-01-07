package com.example.tabapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.View;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity1 extends AppCompatActivity {
    private Adapter adapter;
    private ImageView photo_view;
    private Button btn_random;
    private Button btn_rank;
    private Button btn_address;
    private ImageView imView;
    private String imgUrl = "http://dnllab.incheon.ac.kr/appimg/";
    private Bitmap bmImg;

    private int[] imageIDs = new int[] {
            R.drawable.ball, R.drawable.download, R.drawable.flower, R.drawable.leaf, R.drawable.sky, R.drawable.snowman, R.drawable.apple, R.drawable.bonobono, R.drawable.bubble, R.drawable.flag, R.drawable.frog, R.drawable.frozen, R.drawable.mickey, R.drawable.mouse2020, R.drawable.pororo, R.drawable.ryan, R.drawable.shoe, R.drawable.totoro, R.drawable.tulip, R.drawable.whale,
    };
    private String[] fileNames = new String[]{
            "ball.jpg", "download.jpg", "flower.jpg", "leaf.jpg", "sky.jpg", "snowman.jpg", "apple.jpg", "bonobono.jpg", "bubble.jpg", "flag.jpg", "frog.jpg", "frozen.jpg", "mickey.png", "mouse2020.jpg", "pororo.jpg", "ryan.png", "shoe.jpg", "totoro.jpg", "tulip.jpg", "whale.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost1 = findViewById(R.id.tabHost1);
        tabHost1.setup();
        View tab1View = LayoutInflater.from(MainActivity1.this).inflate(R.layout.selector_layout,null);
        View tab2View = LayoutInflater.from(MainActivity1.this).inflate(R.layout.selector2_layout,null);
        View tab3View = LayoutInflater.from(MainActivity1.this).inflate(R.layout.selector3_layout,null);

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"tab1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setIndicator(tab1View);
        ts1.setContent(R.id.tab1);
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"tab2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setIndicator(tab2View);
        ts2.setContent(R.id.tab2);
        tabHost1.addTab(ts2);

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"tab3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setIndicator(tab3View);
        ts3.setContent(R.id.tab3);
        tabHost1.addTab(ts3);
        init();


        String a = getJsonString();


        btn_address = (Button) findViewById(R.id.btn_address);

        /////////주소연결//////////////////
        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아래 링크를 파라미터를 넘겨준다는 의미.
                new JSONTask().execute("http://192.249.19.252:1280/address");
            }
        });

    }

    //////////////주소연결/////////////////
    public class JSONTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String[] urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", "김우림");
                jsonObject.accumulate("tel", "010-4104-3910");
                HttpURLConnection con = null;
                BufferedReader reader = null;
                Log.d("@@@@@","4");
                try {
                    //URL url = new URL("http://192.249.19.252:1280/address");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    Log.d("@@@@@","URL");
                    con = (HttpURLConnection) url.openConnection();
                    Log.d("@@@@@","con");
                    con.connect();//연결 수행
                    Log.d("@@@@@","connect");
                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();
                    Log.d("@@@@@","InputStream");
                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));
                    Log.d("@@@@@","reader");
                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();
                    Log.d("@@@@@","StringBuffer");
                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";
                    Log.d("@@@@@","line");
                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                        Log.d("@@@@@","3");
                    }
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String… urls) 니까
                    return buffer.toString();
                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("ㅎㅎㅎㅎㅎ", result);
            String address = "{ 'person': " + result + " } ";
            jsonParsing(address);

        }
    }


    private void init() {

        RecyclerView recyclerView = findViewById(R.id.recycler1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity1.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(110);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));




        GridView gridViewImages = findViewById(R.id.gridViewImages);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imageIDs, fileNames);
        gridViewImages.setAdapter(imageGridAdapter);

        photo_view = (ImageView) findViewById(R.id.photo_view);
        btn_random = (Button) findViewById(R.id.btn_random);
        btn_rank = (Button)findViewById(R.id.btn_rank);


        photo_view.setOnClickListener(new View.OnClickListener(){
            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 3);
            }
        });

        btn_random.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, RandomActivity.class);
                startActivity(intent);
            }
        });

        btn_rank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, RankFoodActivity.class);
                startActivity(intent);

            }
        });



    }
    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("person.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return json;
    }
    private void jsonParsing(String json)
    {
        Log.d("아아아아","1022");
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray personArray = jsonObject.getJSONArray("person");

            for(int i=0; i<personArray.length(); i++)
            {
                JSONObject personObject = personArray.getJSONObject(i);

                Person person = new Person();

                person.setName(personObject.getString("name"));
                person.setTel(personObject.getString("tel"));
                adapter.addItem(person);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }
    public void OnClickHandler(View view)
    {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
        final EditText nameEditText = dialogView.findViewById(R.id.name);
        final EditText NicknameEditText = dialogView.findViewById(R.id.nickname);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int pos)
            {
                Person person = new Person();
                person.setName(nameEditText.getText().toString());
                person.setTel(NicknameEditText.getText().toString());
                adapter.addItem(person);
                String name = "이름 : " + nameEditText.getText().toString();
                String nickname = "전화번호 : " + NicknameEditText.getText().toString();
                Toast.makeText(getApplicationContext(),name + "\n" + nickname, Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        adapter.notifyDataSetChanged();
    }

    @Override //갤러리에서 이미지 불러온 후 행동
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 3) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    GridView gridViewImages = findViewById(R.id.gridViewImages);
                    ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imageIDs, fileNames);
                    gridViewImages.setAdapter(imageGridAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public void OnClickHandler2(View view)
//    {
////        final TextView a = findViewById(R.id.name_id);
////        final TextView b = findViewById(R.id.tel_id);
//        View dialogView = getLayoutInflater().inflate(R.layout.dialog, null);
//        final EditText nameEditText = dialogView.findViewById(R.id.name);
//        final EditText NicknameEditText = dialogView.findViewById(R.id.nickname);
////        nameEditText.setText(a.getText());
////        NicknameEditText.setText(b.getText());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialogView);
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int pos)
//            {
//                Person person = new Person();
//                person.setName(nameEditText.getText().toString());
//                person.setTel(NicknameEditText.getText().toString());
////                adapter.changeItem(person,a,b);
//                String name = "이름 : " + nameEditText.getText().toString();
//                String nickname = "전화번호 : " + NicknameEditText.getText().toString();
//
//                Toast.makeText(getApplicationContext(),name + "\n" + nickname, Toast.LENGTH_LONG).show();
//            }
//        });
//
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        adapter.notifyDataSetChanged();
//    }
}



