package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.common.utils.Thing;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class InsertRecord {
    public InsertRecord (Thing thing, AppCompatActivity context) {
        // 初始化
        AVOSCloud.initialize(context, "BwcTgT1z6l8Ynapm4ffxjlk9-MdYXbMMI", "1zpcvxROu34z9WReaFv2w2yG");
        // 上传记录
        AVObject testObject = new AVObject("TodayOnHistory");
        testObject.put("date", thing.getDate());
        testObject.put("content", thing.getContent());
        testObject.put("push", thing.isPush());
        testObject.saveInBackground().subscribe(new Observer<AVObject>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            // 保存成功
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
            public void onComplete() { }
        });
    }
}
