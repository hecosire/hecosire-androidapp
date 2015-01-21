package hecosire.com.hecosireapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DailyNotificationService extends Service {

    private static final String TAG = "DailyNotificationService";

    DailyNotificationAlarm alarm = new DailyNotificationAlarm();

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        alarm.SetAlarm(DailyNotificationService.this);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}