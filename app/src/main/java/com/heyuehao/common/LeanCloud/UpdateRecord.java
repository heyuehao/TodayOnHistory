package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.utils.Thing;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UpdateRecord {
    public UpdateRecord (AppCompatActivity context) {
        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
    }

    /**
     * 根据日期更新内容
     * @param context
     * @param thing
     */
    public void whereDateEqualTo (AppCompatActivity context, Thing thing) {
        AVQuery<AVObject> query = new AVQuery<>("TodayOnHistory");
        query.whereEqualTo("date", thing.getDate());
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> records) {
                // records 是包含满足条件的数组
                // 获取ObjectID更新记录
                if(records.size() == 1) {
                    String id = records.get(0).getObjectId();
                    AVObject item = AVObject.createWithoutData("TodayOnHistory", id);
                    item.put("content", thing.getContent());
                    item.saveInBackground().subscribe(new Observer<AVObject>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(AVObject avObject) { }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, "更新失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(context, "更新成功", Toast.LENGTH_LONG).show();
                            context.finish();
                        }
                    });
                }
            }
            public void onError(Throwable throwable) { }
            public void onComplete() { }
        });
    }
}
