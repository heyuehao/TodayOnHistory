package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.Utils.Loading;
import com.heyuehao.common.Utils.Thing;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UpdateRecord {
    private Loading loading;

    public UpdateRecord (AppCompatActivity context) {
        // 显示加载动画
        loading = new Loading(context);
        loading.start();

        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
    }

    /**
     * 根据日期更新内容
     * @param context
     * @param thing
     */
    public void whereDateEqualTo (AppCompatActivity context, Thing thing) {

        AVQuery<AVObject> userQuery = new AVQuery<>(context.getString(R.string.className));
        AVQuery<AVObject> dateQuery = new AVQuery<>(context.getString(R.string.className));
        dateQuery.whereEqualTo("date", thing.getDate());
        userQuery.whereEqualTo("user", AVUser.getCurrentUser());
        // 组合查询
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(userQuery, dateQuery));

        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> records) {
                // records 是包含满足条件的数组
                // 获取ObjectID更新记录
                if(records.size() == 1) {
                    String id = records.get(0).getObjectId();
                    AVObject item = AVObject.createWithoutData(context.getString(R.string.className), id);
                    item.put("content", thing.getContent());
                    item.saveInBackground().subscribe(new Observer<AVObject>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(AVObject avObject) {
                            Toast.makeText(context, "更新成功", Toast.LENGTH_LONG).show();
                            context.finish();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, "更新失败", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            loading.stop();
                        }
                    });
                }
            }
            public void onError(Throwable throwable) { }
            public void onComplete() { }
        });
    }
}
