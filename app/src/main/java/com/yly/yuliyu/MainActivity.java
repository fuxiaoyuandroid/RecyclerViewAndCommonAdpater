package com.yly.yuliyu;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.yly.yuliyu.allpowerful.CeShiAdapter;
import com.yly.yuliyu.allpowerful.CommonItemClickListener;
import com.yly.yuliyu.allpowerful.CommonItemLongClickListener;
import com.yly.yuliyu.fengexian.GridLayoutItemDecoration;
import com.yly.yuliyu.fengexian.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<String> list;
    private RecyclerViewAdapter mAdapter;
    private CeShiAdapter adapter;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        //mAdapter = new RecyclerViewAdapter(list,this);
        adapter = new CeShiAdapter(this,list,R.layout.home);
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        rv.setAdapter(adapter);

        rv.addItemDecoration(new GridLayoutItemDecoration(this,R.drawable.item_decoration));

        //调用回调接口
        /*mAdapter.setItemClickListener(new RecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClickCaller(int position) {
                Toast.makeText(MainActivity.this,"猎场"+list.get(position)+"计划",Toast.LENGTH_SHORT).show();
            }
        });*/
        //点击事件
        adapter.setCommonItemClickListener(new CommonItemClickListener() {
            @Override
            public void commonItemClickListener(int position) {
                Toast.makeText(MainActivity.this,"猎场"+list.get(position)+"计划",Toast.LENGTH_SHORT).show();
            }
        });
        //长点击事件
        adapter.setCommonItemLongClickListener(new CommonItemLongClickListener() {
            @Override
            public boolean commonItemLongClickListener(int position) {
                Toast.makeText(MainActivity.this,"猎场"+list.get(position)+"失策",Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //滑动方向
                int swipeFlag = ItemTouchHelper.LEFT;
                int dragFlag = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
                    //拖拽方向
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    swipeFlag = 0;
                }else {
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }

                return makeMovementFlags(dragFlag,swipeFlag);

            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 获取原来的位置
                int fromPosition = viewHolder.getAdapterPosition();

                // 得到目标的位置
                int targetPosition = target.getAdapterPosition();

                if (fromPosition > targetPosition) {

                    for (int i = fromPosition; i < targetPosition; i++) {
                        Collections.swap(list, i, i + 1);// 改变实际的数据集
                    }
                } else {
                    for (int i = fromPosition; i > targetPosition; i--) {
                        Collections.swap(list, i, i - 1);// 改变实际的数据集
                    }
                }
                //替换位置
                adapter.notifyItemMoved(fromPosition, targetPosition);
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑删除执行完毕
                int position = viewHolder.getAdapterPosition();
                list.remove(position);
                //动画效果
                adapter.notifyItemRemoved(position);
            }

            /**
             * 长按
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
            }

            /**
             * 还原颜色
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#FF0000"));
                ViewCompat.setTranslationX(viewHolder.itemView,0);
            }
        });

        itemTouchHelper.attachToRecyclerView(rv);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.list_view:
                rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.grid_view:
                rv.setLayoutManager(new GridLayoutManager(this,3));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initData(){
        list = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            list.add(""+(char)i);
        }
    }





    /**
     * 分割线
     */
    private class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration{
        private Paint mPaint;
        public RecyclerViewItemDecoration() {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.RED);
        }

        @Override
        public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(canvas, parent, state);
            //绘制
            int childCount = parent.getChildCount();

            //指定绘制区域
            Rect rect = new Rect();
            //左右间隔
            rect.left = parent.getPaddingLeft();
            rect.right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 1; i < childCount; i++) {
                rect.bottom = parent.getChildAt(i).getTop();
                rect.top = rect.bottom - 10;
                canvas.drawRect(rect,mPaint);
            }

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //获取位置
            int position = parent.getChildAdapterPosition(view);
            //设置分割线的宽度  第一个位置不添加，其余的加到顶部
            if (position != 0){
                outRect.top = 10;
            }
        }
    }

}
