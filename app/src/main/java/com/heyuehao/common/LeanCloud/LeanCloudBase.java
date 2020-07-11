package com.heyuehao.common.LeanCloud;

import com.heyuehao.R;
import com.heyuehao.common.Utils.Loading;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

public class LeanCloudBase {
    private AVQuery<AVObject> query;
    private Loading loading;

    public void init(AppCompatActivity context) {
        // 显示加载动画
        loading = new Loading(context);
        loading.start();

        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        query = new AVQuery<>(context.getString(R.string.className));
    }
}
