package com.heyuehao.common.LeanCloud;

import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.Utils.AskForUpdate;
import com.heyuehao.common.Utils.CreatePv;
import com.heyuehao.common.Utils.CreateRv;
import com.heyuehao.common.Utils.Loading;
import com.heyuehao.common.Utils.Thing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private Loading loading;

    public QueryRecord(AppCompatActivity context) {
        // 显示加载动画
        loading = new Loading(context);
        loading.start();

        // 初始化
        AVOSCloud.initialize(context, context.getString(R.string.appId), context.getString(R.string.appKey));
        query = new AVQuery<>(context.getString(R.string.className));
    }

    /**
     * 查询此用户在当前日期是否有其他记录
     * @param thing
     * @param context
     */
    public void EqualTo(Thing thing, AppCompatActivity context) {
        AVQuery<AVObject> userQuery = new AVQuery<>(context.getString(R.string.className));
        AVQuery<AVObject> dateQuery = new AVQuery<>(context.getString(R.string.className));
        dateQuery.whereEqualTo("date", thing.getDate());
        userQuery.whereEqualTo("user", AVUser.getCurrentUser());
        // 组合查询
        query = AVQuery.and(Arrays.asList(userQuery, dateQuery));
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(List<AVObject> userDate) {
                // records 是返回的查询结果
                res = userDate;
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
                loading.stop();
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
            public void onComplete() {
                // 对数据按日期进行排序
                Collections.sort(res, new Comparator<Thing>() {
                    @Override
                    public int compare(Thing o1, Thing o2) {
                        Long o1Time = null, o2Time = null;
                        try {
                            o1Time = StringToTime(o1.getDate());
                            o2Time = StringToTime(o2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return o2Time.compareTo(o1Time);
                    }
                });

                // 将数据放入适配器中
                CreateRv crv = new CreateRv(res);
                crv.init(context);
                loading.stop();
            }
        });

        return res;
    }

    public void findTodayOnHistory(AppCompatActivity context, String date) {
        AVQuery<AVObject> userQuery = new AVQuery<>(context.getString(R.string.className));
        AVQuery<AVObject> dateQuery = new AVQuery<>(context.getString(R.string.className));
        dateQuery.whereContains("date", date.split("年")[1]);
        userQuery.whereEqualTo("user", AVUser.getCurrentUser());
        // 组合查询
        query = AVQuery.and(Arrays.asList(userQuery, dateQuery));
        List<Thing> res = new ArrayList<>();
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
            public void onError(Throwable e) { }

            @Override
            public void onComplete() {
                // 对数据按日期进行排序
                Collections.sort(res, new Comparator<Thing>() {
                    @Override
                    public int compare(Thing o1, Thing o2) {
                        Long o1Time = null, o2Time = null;
                        try {
                            o1Time = StringToTime(o1.getDate());
                            o2Time = StringToTime(o2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return o1Time.compareTo(o2Time);
                    }
                });

                int index = -1;
                for(int i=0;i<res.size();i++) {
                    if(res.get(i).getDate().equals(date)) {
                        index = i;
                        break;
                    }
                }
                if(index == -1) {
                    index = res.size() - 1;
                }

                // 获取当前日期
                String today = getToday();
                if(today.equals(date.split("年")[1])) {
                    for(Thing t : res) {
                        String tmp = t.getDate().split("年")[0] + "年的今天";
                        t.setDate(tmp);
                    }
                }

                CreatePv cpv = new CreatePv(res);
                cpv.initPager(context, index);
                loading.stop();
            }
        });
    }
    
    public Long StringToTime(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = format.parse(dateStr);
        return date.getTime();
    }

    public String getToday() {
        Calendar calendar = Calendar.getInstance();
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String today = month + "月" + day + "日";
        return today;
    }
}
