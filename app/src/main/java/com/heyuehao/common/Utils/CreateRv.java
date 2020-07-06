package com.heyuehao.common.Utils;

import android.content.Intent;
import android.widget.TextView;

import com.heyuehao.R;
import com.heyuehao.activity.RecordDetail;
import com.heyuehao.common.Adapter.RvAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateRv {
    private RecyclerView mRecycleView;
    private RvAdapter mAdapter; // 适配器
    private LinearLayoutManager mLinearLayoutManager; // 布局管理器
    private TextView view_title, view_thing;
    private List<Thing> data;

    public CreateRv(List<Thing> list) {
        data = list;
    }

    public void init(AppCompatActivity context) {
        // --------配置RecycleView开始
        mRecycleView = context.findViewById(R.id.recyclerView);
        // 创建布局管理器，垂直设置LinearLayoutManager.VERTICAL，水平设置LinearLayoutManager.HORIZONTAL
        mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        // 创建适配器并装入数据
        mAdapter = new RvAdapter(data);
        // 设置布局管理器
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        // 设置适配器
        mRecycleView.setAdapter(mAdapter);
        // Item点击事件
        mAdapter.setOnItemClickListener(new RvAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, RecordDetail.class);
                // 传递点击事件的日期
                intent.putExtra("date", data.get(position).getDate());
                context.startActivity(intent);
            }
        });
        // --------配置RecycleView结束
    }
}
