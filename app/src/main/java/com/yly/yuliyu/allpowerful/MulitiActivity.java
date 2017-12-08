package com.yly.yuliyu.allpowerful;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yly.yuliyu.R;
import com.yly.yuliyu.allpowerful.CommonRecyclerViewAdapter;
import com.yly.yuliyu.allpowerful.CommonRecyclerViewHolder;
import com.yly.yuliyu.allpowerful.MoreTypeSupport;

import java.util.ArrayList;
import java.util.List;

public class MulitiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ChatData> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muliti);
        mList = new ArrayList<>();
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.mrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        recyclerView.setAdapter(new MulitiAdapter(this,mList));


    }

    private void initData() {
        for (int i = 0; i < 30 ; i++) {
            if (i%3==0){
                mList.add(new ChatData("自己发送的消息"+i,1));
            }else {
                mList.add(new ChatData("聊天对方发送的消息"+i,0));
            }
        }
    }

    private class MulitiAdapter extends CommonRecyclerViewAdapter<ChatData>{

        public MulitiAdapter(Context context, List<ChatData> list) {
            super(context, list, new MoreTypeSupport<ChatData>() {
                @Override
                public int getLayoutId(ChatData item) {
                    if (item.getWhichBorder() == 1){
                        return R.layout.chat_me;
                    }
                    return R.layout.chat_other;
                }
            });
        }

        @Override
        protected void convert(CommonRecyclerViewHolder holder, ChatData chatData, int position) {
            holder.setText(R.id.chat,chatData.getMsg());
        }
    }

    private class ChatData{
        private String msg;
        private int whichBorder;

        public ChatData(String msg, int whichBorder) {
            this.msg = msg;
            this.whichBorder = whichBorder;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getWhichBorder() {
            return whichBorder;
        }

        public void setWhichBorder(int whichBorder) {
            this.whichBorder = whichBorder;
        }
    }
}
