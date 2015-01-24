package hecosire.com.hecosireapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends Activity {

    private UserToken userToken;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if (login()) {
            new RetrieveRecordsTask(this, userToken).execute();
            ((MyApplication) getApplication()).reportScreenView("Main view");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        if (id == R.id.action_website) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, MyApplication.RECORDS_URL);
            startActivity(browserIntent);
            return true;
        }

        if (id == R.id.action_refresh) {
            refreshRecords();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void reportHowToFeel(View view) {
        Intent goToNextActivity = new Intent(getApplicationContext(), NewReportActivity.class);
        startActivity(goToNextActivity);
    }

    public void goToWebSiteStats(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, MyApplication.STATS_URL);
        startActivity(browserIntent);
    }

    private void refreshRecords() {
        if (userToken == null) {
            login();
        }
        new RetrieveRecordsTask(this, userToken).execute();
    }

    private void logout() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.emailPreferenceKey));
        editor.remove(getString(R.string.tokenPreferenceKey));
        editor.apply();
        userToken = null;
        login();
    }

    private boolean login() {
        userToken = UserToken.getUserToken(this);
        return userToken != null;
    }

    public void unauthorizedException() {
        Toast.makeText(this, "There is a problem with your credentials. Please login again.", Toast.LENGTH_LONG).show();
        logout();
    }

    public void userRecords(UserRecords records) {
        SimpleAdapter adapter = new SimpleAdapter(this, records.getRecords(),
                android.R.layout.simple_list_item_2,
                new String[]{"title", "date"},
                new int[]{android.R.id.text1,
                        android.R.id.text2});
        listView.setAdapter(adapter);
    }
}
