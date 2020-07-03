package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.utils.Thing;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class InsertRecord {
    public InsertRecord (Thing thing, AppCompatActivity context) {
        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        // 上传记录
        AVObject testObject = new AVObject("TodayOnHistory");
        testObject.put("date", thing.getDate());
        testObject.put("content", thing.getContent());
        testObject.put("push", thing.isPush());
        testObject.saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(AVObject avObject) { }

            @Override
            // 保存失败
            public void onError(Throwable e) {
                Toast.makeText(context, "保存失败，请重新尝试", Toast.LENGTH_LONG).show();
            }

            @Override
            // 保存成功
            public void onComplete() {
                Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
                context.finish();
            }
        });
    }
}
