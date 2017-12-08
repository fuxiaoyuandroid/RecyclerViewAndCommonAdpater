package com.yly.yuliyu.headerfooter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class WrapRecyclerView extends RecyclerView {
    private WrapRvAdapter mAdapter;
    public WrapRecyclerView(Context context) {
        this(context,null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapRvAdapter){
            mAdapter = (WrapRvAdapter) adapter;
        }else {
            mAdapter = new WrapRvAdapter(adapter);

            adapter.registerAdapterDataObserver(mAdapterDataObserver);
        }
        //不是同一个adapter  需要使用观察者模式关联adapter
        super.setAdapter(mAdapter);
    }


    //方法添加移除头底

    public void addHeaderView(View view){
        if (mAdapter != null){
            mAdapter.addHeaderView(view);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void addFooterView(View view){
        if (mAdapter != null){
            mAdapter.addFooterView(view);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void deleteHeaderView(View view){
        if (mAdapter != null){
            mAdapter.deleteHeaderView(view);
        }
        mAdapter.notifyDataSetChanged();
    }

    public void deleteFooterView(View view){
        if (mAdapter != null){
            mAdapter.deleteFooterView(view);
        }
        mAdapter.notifyDataSetChanged();
    }


    private AdapterDataObserver mAdapterDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mAdapter.notifyItemRangeRemoved(positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            mAdapter.notifyItemMoved(fromPosition,toPosition);
        }
    };
}
