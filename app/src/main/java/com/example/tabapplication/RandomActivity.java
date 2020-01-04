package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabapplication.models.Foodcount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;


public class RandomActivity extends AppCompatActivity {
    private TextView textView2;
    private Button btn_choose;
    private Button btn_again;
    private Random rnd = new Random();
    private String[] foodIDs = new String[]{
            "피자", "치킨", "햄버거", "중국집", "라면", "과자", "물", "과일", "보쌈", "족발", "곱창", "떡볶이"
    };
    ArrayList<Foodcount> foodcountlist = new ArrayList<Foodcount>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        textView2 = (TextView) findViewById(R.id.textView2);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        btn_again = (Button)findViewById(R.id.btn_again);

        int num = rnd.nextInt(9);

        textView2.setText(foodIDs[num]);

        final String json = getJsonString();   //json 리스트에서 음식개수 리스트 가져와서 배열 만들기
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray foodcountArray = jsonObject.getJSONArray("Foodcount");

            for(int i=0; i<foodcountArray.length(); i++)
            {
                JSONObject foodcountObject = foodcountArray.getJSONObject(i);

                Foodcount foodcount = new Foodcount();

                foodcount.setfoodname(foodcountObject.getString("foodname"));
                foodcount.setcount(foodcountObject.getInt("count"));

                foodcountlist.add(foodcount);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

            btn_choose.setOnClickListener(new View.OnClickListener() {
                @Override //이미지 불러오기기(갤러리 접근)
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "맛있게 드세요!", Toast.LENGTH_LONG).show();
                    String chosenfood = textView2.getText().toString();
                    JSONObject jsonMain = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        for (int i = 0; i < foodIDs.length; i++) {
//                            if (chosenfood.equals(foodIDs[i])) {
//                                jsonObject.put("count", foodcountlist.get(i).getcount() + 1);     //1을 더해서 넣어줌
//                            } else {
//                                jsonObject.put(foodIDs[i], foodcountlist.get(i).getcount());       //그냥 넣어줌
//                            }
//                            jsonArray.add(jsonObject);
//                        }
//                        jsonMain.put("Foodcount", jsonArray);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            });

        btn_again.setOnClickListener(new View.OnClickListener(){
            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent(RandomActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("Foodcount.json");
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
        try{
            JSONObject jsonObject = new JSONObject(json);

            JSONArray foodcountArray = jsonObject.getJSONArray("Foodcount");

            for(int i=0; i<foodcountArray.length(); i++)
            {
                JSONObject foodcountObject = foodcountArray.getJSONObject(i);

                Foodcount foodcount = new Foodcount();

                foodcount.setfoodname(foodcountObject.getString("foodname"));
                foodcount.setcount(foodcountObject.getInt("count"));

                foodcountlist.add(foodcount);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }








}
