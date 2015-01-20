package hecosire.com.hecosireapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.multidex.MultiDex;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

public class MyApplication  extends Application {

    public static final String TRACKING_ID = "UA-58124371-1";

    public static final String APP_URL = "http://hecosire.com";
    public static final Uri STATS_URL = Uri.parse(APP_URL + "/records/stats");
    public static final Uri RECORDS_URL = Uri.parse(APP_URL +"/records");
    public static final String RECORDS_API_URL = APP_URL + "/api/v1/records";
    public static final String SIGN_IN_URL =  APP_URL + "/api/v1/users/sign_in";

    public enum TrackerName {
        APP_TRACKER
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public MyApplication() {

    }

    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, DailyNotificationService.class));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(TRACKING_ID);

            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

    public void reportScreenView(String name) {
        Tracker t = getTracker(
                MyApplication.TrackerName.APP_TRACKER);

        t.setScreenName(name);

        t.send(new HitBuilders.AppViewBuilder().build());
    }
}
