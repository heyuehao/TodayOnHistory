package com.heyuehao.common.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heyuehao.R;
import com.heyuehao.common.Utils.Thing;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String> titleList;
    private AppCompatActivity context;
    private List<Thing> mData;

    @Override
    public int getCount() {
        return mData.size();
    }

    public ViewPagerAdapter(List<View> viewList, AppCompatActivity context, List<Thing> mData) {
        this.viewList = new ArrayList<>();
        this.viewList = viewList;
        this.context = context;
        this.mData = mData;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    // 实例化
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // 生成View
        View view = context.getLayoutInflater().from(container.getContext()).inflate(R.layout.view_pv, container, false);
        TextView view_title = view.findViewById(R.id.view_title);
        TextView view_thing = view.findViewById(R.id.view_thing);
        Thing thing = mData.get(position);
        view_title.setText(thing.getDate());
        view_thing.setText(thing.getContent());
        container.addView(view);
        return view;
    }

    // 销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
