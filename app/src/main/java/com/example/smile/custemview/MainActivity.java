package com.example.smile.custemview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<PieBean> list=new ArrayList<PieBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyCustemView myView = (MyCustemView) findViewById(R.id.custemview);
        for (int i = 0; i < 10; i++) {
            PieBean pie=new PieBean(i+"",(float)(i*5*2.5));
            list.add(pie);
        }
        myView.setData(list);
    }
}
