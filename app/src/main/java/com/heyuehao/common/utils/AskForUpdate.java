package com.heyuehao.common.utils;

import android.content.DialogInterface;

import com.heyuehao.common.LeanCloud.UpdateRecord;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AskForUpdate {
    AlertDialog builder;
    public void showDialog(AppCompatActivity context, Thing thing, String msg) {
        builder = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 覆盖更新
                        UpdateRecord ur = new UpdateRecord(context);
                        ur.whereDateEqualTo(context, thing);
                    }
                })
                .setNegativeButton("否", null)
                .create();
        builder.show();
    }
}
