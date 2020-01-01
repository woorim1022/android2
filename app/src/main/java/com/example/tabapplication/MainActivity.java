package com.example.tabapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabapplication.adapters.BookmarkAdapter;
import com.example.tabapplication.adapters.ViewpageAdapter;
import com.example.tabapplication.models.Bookmark;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter adapter;
    private EditText et_address;
    private EditText filename;
    private Button btn_add;
    private ListView Iv_bookmark;
    private Button btn_remove;

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
        View tab1View = LayoutInflater.from(MainActivity.this).inflate(R.layout.selector_layout,null);
        View tab2View = LayoutInflater.from(MainActivity.this).inflate(R.layout.selector2_layout,null);
        View tab3View = LayoutInflater.from(MainActivity.this).inflate(R.layout.selector3_layout,null);

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
        jsonParsing(a);



    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.recycler1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        RecyclerDecoration spaceDecoration = new RecyclerDecoration(150);
        recyclerView.addItemDecoration(spaceDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));


        GridView gridViewImages = findViewById(R.id.gridViewImages);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imageIDs, fileNames);
        gridViewImages.setAdapter(imageGridAdapter);

        et_address = (EditText) findViewById(R.id.et_address);
        filename = (EditText) findViewById(R.id.filename);
        btn_add = (Button) findViewById(R.id.btn_add);
        Iv_bookmark = (ListView) findViewById(R.id.Iv_bookmark);
        final BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(this, imageIDs, fileNames);
        Iv_bookmark.setAdapter(bookmarkAdapter);
        SharedPreferences pref=getSharedPreferences("MYPREFERENCE", Activity.MODE_PRIVATE);
        String data = pref.getString("1","");



        final ArrayList<Bookmark> mList = jsonParsingArray(bookmarkAdapter, data);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String address = et_address.getText().toString();
                final String file = filename.getText().toString();
                Bookmark mBook = new Bookmark(address,file);
                mList.add(mBook);
                //데이타 저장
                SharedPreferences sharedPreference = getSharedPreferences("MYPREFERENCE", Context.MODE_MULTI_PROCESS | Context.MODE_PRIVATE);
                setBookmarkArrayPref(sharedPreference, "1", mList);



                Bookmark bookmark = new Bookmark(address,file);
                if(bookmarkAdapter.hasDuplicate(bookmark)) {
                    Toast.makeText(MainActivity.this, getString(R.string.duplicate), Toast.LENGTH_SHORT).show();
                } else {
                    bookmarkAdapter.addBookmark(bookmark);
                    Toast.makeText(MainActivity.this, getString(R.string.complete), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        btn_remove.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                SharedPreferences pref=getSharedPreferences("MYPREFERENCE", Activity.MODE_PRIVATE);
//                String old = pref.getString("1","");
//                ArrayList<Bookmark> mList = jsonParsingArray(bookmarkAdapter, old);
//                mList.remove()
//            }
//        });






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
    private void setBookmarkArrayPref( SharedPreferences pref, String key, ArrayList<Bookmark> values) {
        SharedPreferences.Editor editor = pref.edit();
        JSONArray address = new JSONArray();
        JSONArray file = new JSONArray();
        JSONArray result = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            address.put(values.get(i).getAddres());
            file.put(values.get(i).getFile());

        }
        result.put(address);
        result.put(file);
        if (!values.isEmpty()) {
            editor.putString(key, result.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<Bookmark> jsonParsingArray(BookmarkAdapter bookmarkAdapter, String json) {
        ArrayList<Bookmark> mList = new ArrayList<>();
        try {
            JSONArray mArray = new JSONArray(json);
            JSONArray address = mArray.getJSONArray(0);
            JSONArray file = mArray.getJSONArray(1);
            for (int i = 0; i < address.length(); i++) {
                Bookmark bb = new Bookmark(address.getString(i), file.getString(i));
                bookmarkAdapter.addBookmark(bb);
                mList.add(bb);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
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




