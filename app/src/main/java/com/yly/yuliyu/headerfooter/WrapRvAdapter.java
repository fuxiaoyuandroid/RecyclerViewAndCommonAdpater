package com.yly.yuliyu.headerfooter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class WrapRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //数据列表的Adapter 不包含头部
    private RecyclerView.Adapter mAdapter;

    //头部底部的集合 map
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    // 基本的头部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    // 基本的底部类型开始位置  用于viewType
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;


    public WrapRvAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //区分头部底部还是列表 viewType
        if (mHeaderViews.indexOfKey(viewType)>=0){

            return createHeaderFooterViewHolder(mHeaderViews.get(viewType));
        }else if (mFooterViews.indexOfKey(viewType)>=0){
            return createHeaderFooterViewHolder(mHeaderViews.get(viewType));
        }
        return mAdapter.createViewHolder(parent,viewType);
    }

    private RecyclerView.ViewHolder createHeaderFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public int getItemViewType(int position) {
        //position   viewType  头   中间   底部
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders) {
            return mHeaderViews.keyAt(position);
        }
        //position   viewType 中间
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return mFooterViews.keyAt(adjPosition - adapterCount);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头
        int numHeaders = mHeaderViews.size();
        if (position < numHeaders) {
            return ;
        }
        // viewType 中间
        position = position - mHeaderViews.size();
        mAdapter.onBindViewHolder(holder, position);
        //底
        if (position >= numHeaders + mAdapter.getItemCount()){
            return;
        }
    }

    @Override
    public int getItemCount() {
        // 条数三者相加 = 底部条数 + 头部条数 + Adapter的条数
        return mAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }
    //方法添加移除头底

    public void addHeaderView(View view){
        if (mHeaderViews.indexOfValue(view) < 0){
            //没有，添加
            mHeaderViews.put(BASE_ITEM_TYPE_HEADER++,view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view){
        if (mFooterViews.indexOfValue(view) < 0){
            mFooterViews.put(BASE_ITEM_TYPE_FOOTER++,view);
        }
        notifyDataSetChanged();
    }

    public void deleteHeaderView(View view){
        if (mHeaderViews.indexOfValue(view)>=0){
            mHeaderViews.removeAt(mHeaderViews.indexOfValue(view));
        }
        notifyDataSetChanged();
    }

    public void deleteFooterView(View view){
        if (mFooterViews.indexOfValue(view)>=0){
            mFooterViews.removeAt(mFooterViews.indexOfValue(view));
        }
        notifyDataSetChanged();
    }
}
