package hecosire.com.hecosireapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class NewReportActivity extends Activity {

    private UserToken userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        userToken = UserToken.getUserToken(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    public void reportHealthy(View view) {
        new NewReportTask(this, userToken).execute(1);
    }

    public void reportComingDown(View view) {
        new NewReportTask(this, userToken).execute(2);
    }

    public void reportSick(View view) {
        new NewReportTask(this, userToken).execute(3);
    }

    public void reportRecovering(View view) {
        new NewReportTask(this, userToken).execute(4);
    }

}
