package com.heyuehao.common.LeanCloud;

import com.heyuehao.R;
import com.heyuehao.common.utils.AskForUpdate;
import com.heyuehao.common.utils.Thing;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class QueryRecord {
    private List<AVObject> res;
    public QueryRecord(AppCompatActivity context) {
        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
    }

    /**
     * 查询此日期是否有其他记录
     * @param thing
     * @param context
     */
    public void EqualTo (Thing thing, AppCompatActivity context) {
        AVQuery<AVObject> query = new AVQuery<>("TodayOnHistory");
        query.whereEqualTo("date", thing.getDate());
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> records) {
                // records 是返回的查询结果
                res = records;
            }
            public void onError(Throwable throwable) {}
            public void onComplete() {
                // 若存在记录，询问是否进行更新
                if(res != null && !res.isEmpty()) {
                    AskForUpdate afu = new AskForUpdate();
                    afu.showDialog(context, thing, "当前日期已存在记录，是否覆盖更新");
                } else {
                    // 上传至LeanCloud
                    InsertRecord ir = new InsertRecord(thing, context);
                }
            }
        });
    }
}
