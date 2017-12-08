package com.yly.yuliyu.allpowerful;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/15 0015.
 *
 * 通用ViewHolder
 */

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
    //用于缓存已经找到的界面
    private SparseArray<View> mViews;
    public CommonRecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 从ItemView中获取里面的View
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        //这里多次findViewById,对已找到的View做一个缓存

        View view = mViews.get(viewId);
        if (view == null){
            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    //封装通用功能 文本，图片，点击事件

    /**
     * 文本
     * @param viewId
     * @param text
     * @return
     */
    public CommonRecyclerViewHolder setText(int viewId,CharSequence text){
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;//链式调用
    }

    /**
     * 图片
     * @param viewId
     * @param resourceId
     * @return
     */
    public CommonRecyclerViewHolder setImageResource(int viewId,int resourceId){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * 设置图片通过路径,这里稍微处理得复杂一些，因为考虑加载图片的第三方可能不太一样
     */
    public CommonRecyclerViewHolder setImageByUrl(int viewId,HolderImageLoader loader){
        ImageView imageView = getView(viewId);

        if (loader == null){
            throw new NullPointerException("imageLoader is null");
        }

        loader.loadImage(imageView.getContext(),imageView,loader.getPath());
        return this;
    }

    /**
     * 图片加载  要在实现万能适配器的类中实现
     * 虽然比较复杂，但是方便其他开发人员使用自己喜欢使用的第三方
     */
    public abstract static class HolderImageLoader{
        private String mPath;
        public HolderImageLoader(String path) {
            this.mPath = path;
        }

        public String getPath() {
            return mPath;
        }

        /**
         * 需要去复写这个方法加载图片
         * @param imageView
         * @param path
         */
        public abstract void loadImage(Context context,ImageView imageView, String path);
    }

}
