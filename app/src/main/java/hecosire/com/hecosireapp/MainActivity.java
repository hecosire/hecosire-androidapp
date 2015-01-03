package hecosire.com.hecosireapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    private UserToken userToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (login()) {
            new RetrieveRecordsTask(this, userToken).execute();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selfDestruct(View view) {
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
        editor.commit();
        userToken = null;
        login();
    }

    private boolean login() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String email = sharedPref.getString(getString(R.string.emailPreferenceKey), "MISSING");
        String token = sharedPref.getString(getString(R.string.tokenPreferenceKey), "MISSING");

        if ("MISSING".equals(email) || "MISSING".equals(token)) {
            Intent goToNextActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(goToNextActivity);
            return false;
        }

        userToken = new UserToken(email, token);
        return true;
    }

    public void unauthorizedException() {
        Toast.makeText(this, "There is a problem with your credentials. Please login again.", Toast.LENGTH_LONG).show();
        logout();
    }
}
