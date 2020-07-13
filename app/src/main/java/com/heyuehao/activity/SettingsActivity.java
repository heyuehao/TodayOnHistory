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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        SettingsConfig sc = new SettingsConfig(this);
        String timeStr = hourOfDay + ":" + minute;
        sc.put("pushTime", timeStr);
        // 重新启动服务
        Intent intent = new Intent(this, AlarmService.class);
        startService(intent);
    }
}