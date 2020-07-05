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

        RvAdapter rd = new RvAdapter();
        List<Thing> date = new QueryRecord(ShowRecords.this).QueryAll(ShowRecords.this);
        rd.setmData(date);
    }
}