package com.yly.yuliyu.fengexian;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDrawable;
    public GridLayoutItemDecoration(Context context,int drawableResourceId) {
        mDrawable = ContextCompat.getDrawable(context,drawableResourceId);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //绘制
        drawHorizontalItem(c,parent);

        drawVertical(c,parent);

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        //预留分割线的位置 最底部和最右边不要留

        int bottom = mDrawable.getIntrinsicHeight();

        int right = mDrawable.getIntrinsicWidth();

        if (isLastColumn(view,parent)){//最后一列---最右边
            right = 0;
        }
        if (isLastRow(view,parent)){//最后一行---最底部
            bottom = 0;
        }

        outRect.bottom = bottom;

        outRect.right = right;
    }

    /**
     * 最后一行
     * @return
     */
    private boolean isLastRow(View view,RecyclerView parent) {
        //当前位置 > (行数-1)*列数
        //列数
        int spanCount = getSpanCount(parent);
        //行数 总条目除以列数
        int rowNumber = parent.getAdapter().getItemCount()%spanCount == 0 ? parent.getAdapter().getItemCount()/spanCount:(parent.getAdapter().getItemCount()/spanCount+1);
        Log.d("rowNumber", "isLastRow: "+rowNumber);
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition()+1;


        return currentPosition >(rowNumber - 1)*spanCount;
    }

    /**
     * 最后一列
     * @return
     */
    private boolean isLastColumn(View view,RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition()+1;
        int spanCount = getSpanCount(parent);
        return currentPosition%spanCount == 0;
    }

    //获取列数
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return spanCount;
        }
        return 1;
    }

    /**
     * 绘制垂直方向的分割线
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();

            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(c);
        }
    }

    /**
     * 绘制水平方向分割线
     * @param c
     * @param parent
     */
    private void drawHorizontalItem(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDrawable.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            mDrawable.setBounds(left,top,right,bottom);
            mDrawable.draw(c);
        }
    }


}
