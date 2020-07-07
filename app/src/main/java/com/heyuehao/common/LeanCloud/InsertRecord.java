package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.Utils.Loading;
import com.heyuehao.common.Utils.Thing;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class InsertRecord {
    private Loading loading;

    public InsertRecord (Thing thing, AppCompatActivity context) {
        // 显示加载动画
        loading = new Loading(context);
        loading.start();

        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        // 上传记录
        AVObject record = new AVObject(context.getString(R.string.className));
        record.put("date", thing.getDate());
        record.put("content", thing.getContent());
        record.put("push", thing.isPush());
        record.put("user", thing.getUser());
        record.saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(AVObject avObject) {
                Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                context.finish();
            }

            @Override
            // 保存失败
            public void onError(Throwable e) {
                Toast.makeText(context, "保存失败，请重新尝试", Toast.LENGTH_LONG).show();
            }

            @Override
            // 保存成功
            public void onComplete() {
                loading.stop();
            }
        });
    }
}
