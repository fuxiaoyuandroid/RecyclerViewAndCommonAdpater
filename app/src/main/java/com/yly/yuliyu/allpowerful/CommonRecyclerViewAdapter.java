package com.yly.yuliyu.allpowerful;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * 万能视频器
 * Created by Administrator on 2017/11/15 0015.
 * 必不可少的
 * ViewHolder  点击事件   数据    布局填充   viewType
 */

public abstract class CommonRecyclerViewAdapter<D> extends RecyclerView.Adapter<CommonRecyclerViewHolder>{
    protected Context mContext;
    private LayoutInflater mInflater;
    //数据  泛型
    protected List<D> mList;
    //item布局  通过构造方法传递
    private int mLayoutId;
    //多布局接口
    private MoreTypeSupport moreTypeSupport;

    /**
     * 构造方法  普通
     * @param context
     * @param list
     * @param layoutId
     */
    public CommonRecyclerViewAdapter(Context context, List<D> list,int layoutId) {
        this.mContext = context;
        this.mList = list;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 构造方法 多布局
     * @param context
     * @param list
     * @param support
     */
    public CommonRecyclerViewAdapter(Context context,List<D> list, MoreTypeSupport support) {
        this(context,list,-1);
        this.moreTypeSupport = support;
    }

    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (moreTypeSupport != null){
            mLayoutId = viewType;
        }
        //item布局填充
        View itemView = mInflater.inflate(mLayoutId,parent,false);
        //ViewHolder
        CommonRecyclerViewHolder commonRecyclerViewHolder = new CommonRecyclerViewHolder(itemView);

        return commonRecyclerViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (moreTypeSupport != null){
            return moreTypeSupport.getLayoutId(mList.get(position));
        }
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(CommonRecyclerViewHolder holder, final int position) {
        //优化
        convert(holder,mList.get(position),position);
        //点击事件
        if (commonItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    commonItemClickListener.commonItemClickListener(position);
                }
            });
        }

        if (commonItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    return commonItemLongClickListener.commonItemLongClickListener(position);
                }
            });
        }

    }
    //绑定数据,回传
    protected abstract void convert(CommonRecyclerViewHolder holder, D d, int position);

    @Override
    public int getItemCount() {
        return mList.size()>0?mList.size():0;
    }

    //点击回调接口
    private CommonItemClickListener commonItemClickListener;

    public void setCommonItemClickListener(CommonItemClickListener listener) {
        this.commonItemClickListener = listener;
    }
    //长点击回调接口
    private CommonItemLongClickListener commonItemLongClickListener;

    public void setCommonItemLongClickListener(CommonItemLongClickListener listener) {
        this.commonItemLongClickListener = listener;
    }
}
