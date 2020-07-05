package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.Utils.AskForUpdate;
import com.heyuehao.common.Utils.Thing;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class QueryRecord {
    private List<AVObject> res;
    private AVQuery<AVObject> query;
    public QueryRecord(AppCompatActivity context) {
        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        query = new AVQuery<>(context.getString(R.string.className));
    }

    /**
     * 查询此日期是否有其他记录
     * @param thing
     * @param context
     */
    public void EqualTo(Thing thing, AppCompatActivity context) {
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

    /**
     * 查询当前用户所有记录
     * @param context
     * @return
     */
    public List<Thing> QueryAll(AppCompatActivity context) {
        List<Thing> res = new ArrayList<>();
        AVUser user = AVUser.getCurrentUser();
        AVQuery<AVObject> query = new AVQuery<>(context.getString(R.string.className));
        query.whereEqualTo("user", user);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(List<AVObject> avObjects) {
                for(int i=0;i<avObjects.size();i++) {
                    Thing item = new Thing();
                    String content = (String) avObjects.get(i).get("content");
                    String date = (String) avObjects.get(i).get("date");
                    item.setContent(content);
                    item.setDate(date);
                    res.add(item);
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "获取记录失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() { }
        });

        return res;
    }
}