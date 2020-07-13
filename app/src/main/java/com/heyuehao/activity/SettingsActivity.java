package com.heyuehao.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.heyuehao.R;
import com.heyuehao.common.Config.SettingsConfig;
import com.heyuehao.common.Service.AlarmService;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private MaterialButton pushTime, pushSwitch;
    private TimePickerDialog timePickerDialog;
    private FragmentManager fragmentManager;
    private SettingsConfig sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sc = new SettingsConfig(this);
        pushTime = findViewById(R.id.push_time);
        pushTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                TimePicker(hour, minute);
            }
        });

        pushSwitch = findViewById(R.id.push_switch);
        String push = sc.get("doPush");
        if("false".equals(push)) {
            pushSwitch.setText("开");
        } else {
            pushSwitch.setText("关");
        }
        pushSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateSwitch();
            }
        });
    }

    public void TimePicker(int hour, int minute) {
        timePickerDialog = TimePickerDialog.newInstance(this, hour, minute, true);
        timePickerDialog.setThemeDark(false);
        timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        timePickerDialog.vibrate(false);
        fragmentManager = getSupportFragmentManager();
        timePickerDialog.setOnCancelListener(null);
        timePickerDialog.show(fragmentManager, "TimePickerDialog");
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String timeStr = hourOfDay + ":" + minute;
        sc.put("pushTime", timeStr);
        String doPush = sc.get("doPush");
        if(!"false".equals(doPush)) {
            // 重新启动服务
            Intent intent = new Intent(this, AlarmService.class);
            startService(intent);
        }
    }

    public void startPush() {
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
        sc.put("doPush", "true");
    }

    public void stopPush() {
        Intent intent = new Intent(this, AlarmService.class);
        stopService(intent);
        sc.put("doPush", "false");
    }

    public void stateSwitch() {
        String state = sc.get("doPush");
        switch (state) {
            case "true":
                stopPush();
                pushSwitch.setText("开");
                break;
            case "false":
                startPush();
                pushSwitch.setText("关");
                break;
            default:
                stopPush();
                pushSwitch.setText("开");
        }
    }
}