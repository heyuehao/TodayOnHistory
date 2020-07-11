package com.heyuehao.common.Utils;

import android.view.LayoutInflater;
import android.view.View;

import com.heyuehao.R;
import com.heyuehao.common.Adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import me.relex.circleindicator.CircleIndicator;

public class CreatePv {
    AppCompatActivity mcontext;
    private ViewPager mViewPager;
    private List<Thing> mData;
    private List<View> viewList;

    public CreatePv(List<Thing> list) {
        mData = list;
    }

    /**
     * 配置ViewPager
     * @param context
     */
    public void initPager (AppCompatActivity context, int index) {
        mcontext = context;
        mViewPager = mcontext.findViewById(R.id.detail_view_pager);
        viewList = new ArrayList<>();
        LayoutInflater li = context.getLayoutInflater();

        viewList.add(li.inflate(R.layout.view_pv, null, false));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(viewList, mcontext, mData);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setCurrentItem(index);
        // 添加小圆点指示器
        CircleIndicator indicator = mcontext.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }
}
