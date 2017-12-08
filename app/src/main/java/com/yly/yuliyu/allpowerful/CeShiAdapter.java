package com.yly.yuliyu.allpowerful;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yly.yuliyu.R;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 实现万能适配器
 */

public class CeShiAdapter extends CommonRecyclerViewAdapter<String> {
    public CeShiAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    protected void convert(CommonRecyclerViewHolder holder, String s, int position) {
        //绑定数据 找到View
        holder.setText(R.id.tv,s);
        holder.setImageResource(R.id.iv,R.mipmap.ic_launcher);
        /*holder.setImageByUrl(R.id.tv,new ImageGet(s));*/
    }

    //声明自己常用的第三方图片加载
    public class ImageGet extends CommonRecyclerViewHolder.HolderImageLoader{

        public ImageGet(String path) {
            super(path);
        }

        @Override
        public void loadImage(Context context,ImageView imageView, String path) {
            Picasso.with(context).load(path).into(imageView);
        }
    }
}
