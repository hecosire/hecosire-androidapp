package hecosire.com.hecosireapp;

import android.app.Application;
import android.content.Intent;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

public class MyApplication  extends Application {

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



    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker("UA-58124371-1");

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
