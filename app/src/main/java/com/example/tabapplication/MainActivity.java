package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.tabapplication.adapters.BookmarkAdapter;
import com.example.tabapplication.models.Bookmark;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    private EditText et_address;
    private Button btn_add;
    private ListView Iv_bookmark;

    private int[] imageIDs = new int[] {
            R.drawable.ball,
            R.drawable.download,
            R.drawable.flower,
            R.drawable.leaf,
            R.drawable.sky,
            R.drawable.snowman,
            R.drawable.apple,
            R.drawable.bonobono,
            R.drawable.bubble,
            R.drawable.flag,
            R.drawable.frog,
            R.drawable.frozen,
            R.drawable.mickey,
            R.drawable.mouse2020,
            R.drawable.pororo,
            R.drawable.ryan,
            R.drawable.shoe,
            R.drawable.totoro,
            R.drawable.tulip,
            R.drawable.whale,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"tab1")
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.tab1);
        ts1.setIndicator("TAB 1");
        tabHost1.addTab(ts1);

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"tab2")
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.tab2);
        ts2.setIndicator("TAB 2");
        tabHost1.addTab(ts2);

        // 세 번째 Tab. (탭 표시 텍스트:"TAB 3"), (페이지 뷰:"tab3")
        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.tab3);
        ts3.setIndicator("TAB 3");
        tabHost1.addTab(ts3);

        init();

        //json주소반환
        String a = getJsonString();
        jsonParsing(a);
    }


    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(70);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        //그리드뷰
        GridView gridViewImages = (GridView)findViewById(R.id.gridViewImages);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imageIDs);
        gridViewImages.setAdapter(imageGridAdapter);

        et_address = (EditText) findViewById(R.id.et_address);
        btn_add = (Button) findViewById(R.id.btn_add);
        Iv_bookmark = (ListView) findViewById(R.id.Iv_bookmark);

        final BookmarkAdapter bookmarkAdapter = new BookmarkAdapter();
        Iv_bookmark.setAdapter(bookmarkAdapter);

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String address = et_address.getText().toString();
                Bookmark bookmark = new Bookmark(address);
                if(bookmarkAdapter.hasDuplicate(bookmark)) {
                    Toast.makeText(MainActivity.this, getString(R.string.duplicate), Toast.LENGTH_SHORT).show();
                } else {
                     bookmarkAdapter.addBookmark(bookmark);
                    Toast.makeText(MainActivity.this, getString(R.string.complete), Toast.LENGTH_SHORT).show();
                }
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


}




