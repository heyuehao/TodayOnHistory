package com.heyuehao.common.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.activity.ShowRecords;
import com.heyuehao.common.Utils.Thing;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Thing> mData;

    public RvAdapter(List<Thing> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType")
        View view = mLayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 设置每个TextView的内容
        holder.item_date.setText(mData.get(position).getDate());
        holder.item_content.setText(mData.get(position).getContent());

        // 短按
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onClick(position);
                }
            }
        });

        // 长按
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
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

    // ----------------短按点击事件开始
    // 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    // 公共方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    // ----------------短按点击事件结束

    // ----------------长按点击事件开始
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }
    // ----------------长按点击事件结束
}
