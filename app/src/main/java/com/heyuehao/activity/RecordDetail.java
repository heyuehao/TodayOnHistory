package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.heyuehao.R;
import com.heyuehao.common.LeanCloud.QueryRecord;

public class RecordDetail extends AppCompatActivity {
    private Toolbar toolbar_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 配置toolbar
        toolbar_detail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar_detail);
        toolbar_detail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            // Toolbar点击事件
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_item:
                        Toast.makeText(RecordDetail.this, "delete", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });

        // 查询往年同月同日的数据
        Intent intent = getIntent();
        QueryRecord qr = new QueryRecord(this);
        qr.findTodayOnHistory(this, intent.getStringExtra("date"));
    }

    // 创建Menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
}