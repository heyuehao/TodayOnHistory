package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.heyuehao.R;
import com.heyuehao.common.Adapter.RvAdapter;
import com.heyuehao.common.LeanCloud.QueryRecord;
import com.heyuehao.common.Utils.Thing;

import java.util.List;

public class ShowRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        // 点击返回
        findViewById(R.id.backImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 从LeanCloud中查询当前用户的所有数据
        QueryRecord qr = new QueryRecord(this);
        qr.QueryAll(this);
    }
}