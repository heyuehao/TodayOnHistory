package com.heyuehao.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.heyuehao.R;
import com.heyuehao.common.LeanCloud.DeleteRecord;
import com.heyuehao.common.LeanCloud.QueryRecord;

public class RecordDetail extends AppCompatActivity {
    private Toolbar toolbar_detail;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        // 查询往年同月同日的数据
        Intent intent = getIntent();
        this.date = intent.getStringExtra("date");
        QueryRecord qr = new QueryRecord(this);
        qr.findTodayOnHistory(this, date);

        // 配置toolbar
        toolbar_detail = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar_detail);
        toolbar_detail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            // Toolbar点击事件
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_item:
                        isSure();
                        break;
                }
                return true;
            }
        });
    }

    // 创建Menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    public void isSure() {
        AlertDialog builder = new AlertDialog.Builder(this)
                .setMessage("确定要删除该事件吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteRecord deleteRecord = new DeleteRecord(RecordDetail.this);
                        deleteRecord.delete(date);
                    }
                }).setNegativeButton("取消", null)
                .create();
        builder.show();
    }
}