package com.heyuehao.common.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.heyuehao.common.Service.AlarmService;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        if(intent.getAction().equals(ACTION_BOOT)) {
            Intent in = new Intent(context, AlarmService.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(in);
        }
    }
}
