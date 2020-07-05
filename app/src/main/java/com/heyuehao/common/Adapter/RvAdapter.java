package com.heyuehao.common.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heyuehao.R;
import com.heyuehao.common.Utils.Thing;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Thing> mData;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType")
        View view = mLayoutInflater.inflate(R.id.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 设置每个TextView的内容
        holder.item_date.setText(mData.get(position).getDate());
        holder.item_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // item点击事件
            }
        });

        holder.item_content.setText(mData.get(position).getContent());
        holder.item_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 创建VIewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_date, item_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_date = itemView.findViewById(R.id.text_item_date);
            item_content = itemView.findViewById(R.id.text_item_content);
        }
    }

    public void setmData(List<Thing> mData) {
        this.mData = mData;
    }
}
