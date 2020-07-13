package com.heyuehao.common.Config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.heyuehao.common.Service.AlarmService;


public class SettingsConfig {
    SharedPreferences preferences;
    Context context;

    public SettingsConfig(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("config", context.MODE_PRIVATE);
    }

    public void put(String name, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name, value);
        editor.commit();
        // 重新启动服务
        Intent intent = new Intent(context, AlarmService.class);
        context.startService(intent);
        Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
    }

    public String get(String name) {
        return preferences.getString(name, "default value");
    }
}
