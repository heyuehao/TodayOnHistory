package com.heyuehao.common.LeanCloud;

import android.content.Intent;
import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.activity.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserSignUp {
    private AVUser user;

    public UserSignUp() {
        user = new AVUser();
    }
    public void SignUp(AppCompatActivity context, String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground().subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVUser avUser) {
                // 注册成功
                user = avUser;
                Toast.makeText(context,"注册成功", Toast.LENGTH_LONG).show();
                System.out.println("注册成功 objectId：" + user.getObjectId());
            }
            public void onError(Throwable throwable) {
                // 注册失败（通常是因为用户名已被使用）
                Toast.makeText(context,"注册失败，请重试", Toast.LENGTH_LONG).show();
            }
            public void onComplete() {
                // 注册成功，执行登录操作
                UserLogIn ul = new UserLogIn();
                ul.LogIn(context, username, password);
                // 结束注册页面activity
                context.finish();
            }
        });
    }

    /**
     * 查询该用户名是否已存在
     * @param context
     * @param username
     * @param password
     * @return
     */
    public boolean isExist(AppCompatActivity context, String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
        AVQuery<AVObject> query = new AVQuery<>(context.getString(R.string.className));
        query.whereEqualTo("user", user);
        AVObject obj = query.getFirst();
        if(obj == null) {
            return false;
        }
        return true;
    }
}
