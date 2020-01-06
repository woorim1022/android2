package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class RankFoodActivity extends AppCompatActivity {
    private RankFoodAdapter adapter;
    private ArrayList<Foodcount> foodcountlist = new ArrayList<Foodcount>();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_food);

        textView = (TextView) findViewById(R.id.tv_foodname);




        RecyclerView recyclerView = findViewById(R.id.ranklist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RankFoodActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RankFoodAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(250);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));



        final String json = getJsonString();   //json 리스트에서 음식개수 리스트 가져와서 배열 만들기
        jsonParsing(json);



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
                adapter.addItem(foodcount);

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }





}
