package hecosire.com.hecosireapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import hecosire.com.hecosireapp.smartwatch.HealthyState;


public class NewReportActivity extends Activity {

    private UserToken userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        userToken = UserToken.getUserToken(this);
        ((MyApplication)getApplication()).reportScreenView("New report");

        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.cancel(DailyNotificationAlarm.NOTIFICATION_ID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public void reportHealthy(View view) {
        new NewReportTask(this, userToken).execute(HealthyState.HEALTHY);
    }

    public void reportComingDown(View view) {
        new NewReportTask(this, userToken).execute(HealthyState.COMING_DOWN);
    }

    public void reportSick(View view) {
        new NewReportTask(this, userToken).execute(HealthyState.SICK);
    }

    public void reportRecovering(View view) {
        new NewReportTask(this, userToken).execute(HealthyState.RECOVERING);
    }

}
