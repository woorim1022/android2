package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tabapplication.models.Foodcount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import static java.sql.DriverManager.println;


public class RandomActivity extends AppCompatActivity {
    private TextView textView2;
    private Button btn_choose;
    private Button btn_again;
    private Random rnd = new Random();
    private String[] foodIDs = new String[]{
            "피자", "치킨", "햄버거", "중국집", "라면", "과자", "물", "과일", "보쌈", "족발", "곱창", "떡볶이"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);



        textView2 = (TextView) findViewById(R.id.textView2);
        btn_choose = (Button) findViewById(R.id.btn_choose);
        btn_again = (Button)findViewById(R.id.btn_again);








        final int num = rnd.nextInt(11);
        textView2.setText(foodIDs[num]);




        String foodjson = getJsonString();
        Log.d("오예오예", foodjson);
        try {
            JSONObject jsonObject = new JSONObject(foodjson);
            JSONArray foodcountArray = jsonObject.getJSONArray("Foodcount");
            final Object count = foodcountArray.getJSONObject(num).get("count");
            Log.d("우우우우",  count.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }




        btn_choose.setOnClickListener(new View.OnClickListener() {
                @Override //이미지 불러오기기(갤러리 접근)
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "맛있게 드세요!", Toast.LENGTH_LONG).show();
                    Foodcount foodcount = new Foodcount();
                    String foodjson = getJsonString();
                    try {
                        JSONObject jsonObject = new JSONObject(foodjson);
                        JSONArray foodcountArray = jsonObject.getJSONArray("Foodcount");
                        Object count = foodcountArray.getJSONObject(num).get("count");
                        int a = (int)count;
                        foodcount.setfoodname(foodIDs[num]);
                        foodcount.setcount(a);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    request(foodcount);
                    new RandomActivity.JSONTask().execute("http://192.249.19.252:1280/foodcount");

                }
            });



        btn_again.setOnClickListener(new View.OnClickListener(){
            @Override //이미지 불러오기기(갤러리 접근)
            public void onClick(View v) {
                Intent intent = new Intent(RandomActivity.this, RandomActivity.class);
                startActivity(intent);
            }
        });
    }







    public class JSONTask extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String[] urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("foodname", "김우림");
                jsonObject.accumulate("count", 1022);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                Log.d("@@@@@","9999");
                try {
                    //URL url = new URL("http://192.249.19.252:1280/foodcount");
                    Log.d("@@@@@","7777");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    Log.d("@@@@@","url");
                    con = (HttpURLConnection) url.openConnection();
                    Log.d("@@@@@","con");
                    con.connect();//연결 수행
                    Log.d("@@@@@","connect");
                    //입력 스트림 생성
                    InputStream stream2 = con.getInputStream();
                    Log.d("@@@@@","InputStream");
                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream2));
                    Log.d("@@@@@","reader");
                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();
                    Log.d("@@@@@","buffer");
                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";
                    Log.d("@@@@@",line);
                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                        Log.d("@@@@@","8888");
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
            Log.d("ㅂㅂㅂㅂㅂ", result);
            String address = "{ 'Foodcount': " + result + " } ";
            jsonParsing(address);

        }
    }













    public void request(Foodcount food){
        //url 요청주소 넣는 editText를 받아 url만들기
        String url = "http://192.249.19.252:1280/foodcount";

        //JSON형식으로 데이터 통신을 진행합니다!
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put해줍니다 : 데이터를 json형식으로 바꿔 넣어주었습니다.
            testjson.put("foodname", food.getfoodname());
            testjson.put("count", food.getcount());
            String jsonString = testjson.toString(); //완성된 json 포맷

            //이제 전송해볼까요?
            final RequestQueue requestQueue = Volley.newRequestQueue(RandomActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례입니다.
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        println("데이터전송 성공");

                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());
                        String address = "{ 'Foodcount': " + jsonObject + " } ";
                        //foodcount.json 에 저장하는 코드


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }








    private String getJsonString()
    {
        String json = "";

        try {
            InputStream is = getAssets().open("foodcount.json");
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
        Log.d("jsonparsing",json);
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray foodcountArray = jsonObject.getJSONArray("Foodcount");
            for(int i=0; i<foodcountArray.length(); i++)
            {
                JSONObject foodcountObject = foodcountArray.getJSONObject(i);

                Foodcount foodcount = new Foodcount();

                foodcount.setfoodname(foodcountObject.getString("foodname"));
                foodcount.setcount(foodcountObject.getInt("count"));

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }





//test
}
