package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;
import cn.leancloud.AVOSCloud;
import cn.leancloud.AVUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.heyuehao.R;
import com.heyuehao.common.LeanCloud.UserLogIn;
import com.heyuehao.common.LeanCloud.UserSignUp;

public class Welcome extends AppCompatActivity {
    private EditText usernameText, passwordText;
    private Button signUpBtn, loginBtn;
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 判断是否已登录
        if(isLogin()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            this.finish();
        }

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        loginBtn = findViewById(R.id.logInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                UserSignUp us = new UserSignUp();
                us.SignUp(Welcome.this, username, password);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                UserLogIn ul = new UserLogIn();
                ul.LogIn(Welcome.this, username, password);
            }
        });
    }

    public boolean isLogin() {
        AVOSCloud.initialize(this, this.getString(R.string.appId), this.getString(R.string.appKey));
        if(AVUser.getCurrentUser() == null) {
            return false;
        }
        return true;
    }
}