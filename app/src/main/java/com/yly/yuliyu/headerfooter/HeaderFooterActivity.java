package com.yly.yuliyu.headerfooter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yly.yuliyu.R;
import com.yly.yuliyu.fengexian.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeaderFooterActivity extends AppCompatActivity {
    private WrapRecyclerView hfrv;
    private List<String> list;
    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);
        initData();
        adapter = new RecyclerViewAdapter(list,this);
        hfrv = (WrapRecyclerView) findViewById(R.id.hfrv);
        hfrv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        hfrv.setAdapter(adapter);
        final View view = LayoutInflater.from(this).inflate(R.layout.layout_header,hfrv,false);
        hfrv.addHeaderView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hfrv.deleteHeaderView(view);
            }
        });

        adapter.setItemClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClickCaller(int position) {
                Toast.makeText(HeaderFooterActivity.this,"删除"+list.get(position),Toast.LENGTH_SHORT).show();
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public void initData(){
        list = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            list.add(""+(char)i);
        }
    }
}
