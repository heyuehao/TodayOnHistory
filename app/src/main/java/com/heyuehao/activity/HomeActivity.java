package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.heyuehao.R;
import com.heyuehao.common.LeanCloud.UserLogOut;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private MaterialButton addBtn, queryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 配置toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        Toast.makeText(HomeActivity.this, "settings", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logout:
                        UserLogOut ul = new UserLogOut();
                        boolean flag = ul.Logout();
                        if (flag) {
                            Toast.makeText(HomeActivity.this, "退出成功", Toast.LENGTH_LONG).show();
                            // 跳转到登录页面
                            Intent intent = new Intent(HomeActivity.this, Welcome.class);
                            finish();
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });

        addBtn = findViewById(R.id.addBtn);
        queryBtn = findViewById(R.id.queryBtn);

        // 点击跳转到添加事件页面
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddRecord.class);
                startActivity(intent);
            }
        });

        // 点击跳转到查看事件页面
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShowRecords.class);
                startActivity(intent);
            }
        });
    }

    // 创建Menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}