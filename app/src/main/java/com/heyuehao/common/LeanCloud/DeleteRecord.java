package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.Adapter.RvAdapter;
import com.heyuehao.common.Utils.Loading;
import com.heyuehao.common.Utils.Thing;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import cn.leancloud.types.AVNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DeleteRecord extends LeanCloudBase {
    private AVQuery<AVObject> query;
    private Loading loading;
    private AppCompatActivity context;

    public DeleteRecord(AppCompatActivity context) {
        // 显示加载动画
        loading = new Loading(context);
        loading.start();

        // 初始化
        this.context = context;
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        query = new AVQuery<>(context.getString(R.string.className));
    }

    public void delete(String date) {
        AVQuery<AVObject> userQuery = new AVQuery<>(context.getString(R.string.className));
        AVQuery<AVObject> dateQuery = new AVQuery<>(context.getString(R.string.className));
        userQuery.whereEqualTo("user", AVUser.getCurrentUser());
        dateQuery.whereEqualTo("date", date);
        // 组合查询
        query = AVQuery.and(Arrays.asList(userQuery, dateQuery));

        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(List<AVObject> avObjects) {
                if(avObjects.size() == 1) {
                    // 获取事件id
                    String id = avObjects.get(0).getObjectId();
                    // 删除操作
                    AVObject item = AVObject.createWithoutData(context.getString(R.string.className), id);
//                    item.delete();
                    item.deleteInBackground().subscribe(new Observer<AVNull>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(AVNull avNull) { }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(context, "删除失败，请重试", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {
                            Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();
                            delectRecycleViewItem(date);
                        }
                    });
                } else {
                    Toast.makeText(context, "操作异常，删除失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() { }
        });
    }
    
    public void delectRecycleViewItem(String date) {
        // 从RecycleView中删除该事件
        RvAdapter rap = RvAdapter.getInstance();
        List<Thing> Data = rap.getmData();
        int position = -1;
        for(int i=0;i<Data.size();i++) {
            if(Data.get(i).getDate().equals(date)) {
                position = i;
                break;
            }
        }
        if(position >= 0 && position < Data.size()) {
            rap.notifyItemRemoved(position);
        } else {
            // 从LeanCloud中查询当前用户的所有数据并传递给适配器
            QueryRecord qr = new QueryRecord(context);
            qr.QueryAll(context);
        }
        loading.stop();
        context.finish();
    }
}