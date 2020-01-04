package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.simple.JSONObject;

import static java.sql.DriverManager.println;

public class RankFoodActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_food);

        editText = (EditText) findViewById(R.id.editText);

        //////서버통신////////////////
        /////



        RankFoodAdaper RankFoodAdapter = new RankFoodAdaper(this);
        ListView ranklist = findViewById(R.id.ranklist);
        ranklist.setAdapter(RankFoodAdapter);





    }


}
