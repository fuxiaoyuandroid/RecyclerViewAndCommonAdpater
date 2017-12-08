package com.yly.yuliyu.fengexian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yly.yuliyu.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private List<String> mList;
    private Context mContext;

    public RecyclerViewAdapter(List<String> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.home,parent,false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(itemView);
        return rvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        holder.tv.setText(mList.get(position));
        if (mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //利用回调接口
                    mItemClickListener.onItemClickCaller(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    //声明回调接口
    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    //点击事件
    public interface ItemClickListener{

        public void onItemClickCaller(int position);

    }

}
