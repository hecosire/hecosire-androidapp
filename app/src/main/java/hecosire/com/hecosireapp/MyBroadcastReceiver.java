package hecosire.com.hecosireapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Intent startServiceIntent = new Intent(context, DailyNotificationService.class);
            context.startService(startServiceIntent);
        }

    }
}