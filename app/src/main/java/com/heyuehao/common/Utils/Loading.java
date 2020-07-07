package com.heyuehao.common.Utils;

import com.heyuehao.R;
import com.victor.loading.rotate.RotateLoading;

import androidx.appcompat.app.AppCompatActivity;

public class Loading {
    private RotateLoading rotateLoading;
    private AppCompatActivity context;

    public Loading(AppCompatActivity context) {
        this.context = context;
        rotateLoading = this.context.findViewById(R.id.rotateloading);
    }

    /**
     * 显示加载动画
     */
    public void start() {
        rotateLoading.start();
    }

    /**
     * 取消加载动画
     */
    public void stop() {
        if(rotateLoading.isStart()) {
            rotateLoading.stop();
        } else {
            context.finish();
        }
    }
}
