package com.heyuehao.common.LeanCloud;

import android.content.Intent;
import android.widget.Toast;

import com.heyuehao.activity.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UserLogIn {
    private AVUser user;
    public void LogIn(AppCompatActivity context, String username, String password) {
        AVUser.logIn(username, password).subscribe(new Observer<AVUser>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVUser avUser) {
                // 登录成功
                user = avUser;
                Toast.makeText(context,"登录成功", Toast.LENGTH_LONG).show();
            }
            public void onError(Throwable throwable) {
                // 登录失败（可能是密码错误）
                Toast.makeText(context,"登录失败，请检查用户名或密码", Toast.LENGTH_LONG).show();
            }
            public void onComplete() {
                Intent intent = new Intent(context,HomeActivity.class);
                context.finish();
                context.startActivity(intent);
            }
        });
    }
}
