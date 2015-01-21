package hecosire.com.hecosireapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class DailyNotificationAlarm extends BroadcastReceiver {

    private static final String TAG = "DailyNotificationAlarm";

    public static int NOTIFICATION_ID = 001;
    public static int ALARM_ID = 002;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        newReportNotification(context, intent);
        wl.release();
    }


    private void newReportNotification(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_white)
                        .setContentTitle("How are you?")
                        .setContentText("Report how are you feeling..");


        Intent resultIntent = new Intent(context, NewReportActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public void SetAlarm(Context context) {
        CancelAlarm(context);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar nextAlarm = getNextAlarmCalendar();

        PendingIntent pi = createAlarmPendingIntent(context);

        Log.d(TAG, "Alarm set to: " + nextAlarm.getTime().toString());
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, nextAlarm.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pi); // Millisec * Second * Minute
    }

    private Calendar getNextAlarmCalendar() {
        Calendar now = Calendar.getInstance();

        Calendar nextAlarm = Calendar.getInstance();
        nextAlarm.setTimeInMillis(System.currentTimeMillis());
        nextAlarm.set(Calendar.HOUR_OF_DAY, 20);
        nextAlarm.set(Calendar.MINUTE, 00);
        nextAlarm.set(Calendar.SECOND, 00);

        if (tooLateForToday(now, nextAlarm)) {
            nextAlarm.add(Calendar.DAY_OF_MONTH, 1);
            nextAlarm.set(Calendar.HOUR_OF_DAY, 8);
        }
        return nextAlarm;
    }

    private boolean tooLateForToday(Calendar now, Calendar nextAlarm) {
        return now.get(Calendar.HOUR_OF_DAY) >= nextAlarm.get(Calendar.HOUR_OF_DAY) &&
                now.get(Calendar.MINUTE) >= nextAlarm.get(Calendar.MINUTE);
    }

    public void CancelAlarm(Context context) {
        try {
            PendingIntent sender = createAlarmPendingIntent(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(sender);
        } catch (Exception ex) {
            Log.e("DailyNotificationAlarm", "Cancel Alarm exception",ex);
        }
    }

    private PendingIntent createAlarmPendingIntent(Context context) {
        Intent i = new Intent(context, DailyNotificationAlarm.class);
        return PendingIntent.getBroadcast(context, ALARM_ID, i, 0);
    }

}