package com.example.tabapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TabHost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // 리사이클러뷰에 표시할 데이터 리스트 생성.
    RecyclerView recyclerView = null;
    Adapter adapter = null;
    ArrayList<Person> list = new ArrayList<Person>();
    Person a = new Person();

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

        recyclerView = findViewById(R.id.recycler1) ;
        // 리사이클러뷰에 Adapter 객체 지정.
        adapter = new Adapter(list) ;
        recyclerView.setAdapter(adapter);


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        addPerson("정예준","010-2795-2608");
        adapter.notifyDataSetChanged();
//        JSONObject jsonObject = new JSONObject();
//        JSONObject data1 = new JSONObject();
//        data1.put("name", "정예준");
//        data1.put("번호", "010-2795-2608");
//        JSONArray array1 = new JSONArray();
//        array1.add(data1);
//        jsonObject.put("first",array1);

    }
    public void addPerson(String name, String tel) {
        Person person = new Person();

        person.setName(name);
        person.setTel(tel);
        list.add(person);
    }
}
