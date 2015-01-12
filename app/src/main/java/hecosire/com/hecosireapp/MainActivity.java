package hecosire.com.hecosireapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    private UserToken userToken;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        if (login()) {
            new RetrieveRecordsTask(this, userToken).execute();
            ((MyApplication) getApplication()).reportScreenView("Main view");
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

    public void reportHowToFeel(View view) {
        Intent goToNextActivity = new Intent(getApplicationContext(), NewReportActivity.class);
        startActivity(goToNextActivity);
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
        userToken = UserToken.getUserToken(this);
        return userToken != null;
    }

    public void unauthorizedException() {
        Toast.makeText(this, "There is a problem with your credentials. Please login again.", Toast.LENGTH_LONG).show();
        logout();
    }

    public void userRecords(JSONArray feed) {

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        for (int i = 0; i < feed.length(); i++) {
            try {
                JSONObject jsonobject = feed.getJSONObject(i);
                JSONObject health_state = jsonobject.getJSONObject("health_state");

                String name = health_state.getString("name");
                String created_at = jsonobject.getString("created_at");

                Map<String, String> datum = new HashMap<String, String>(2);

                datum.put("title", name);
                datum.put("date", created_at);
                data.add(datum);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "date"},
                new int[]{android.R.id.text1,
                        android.R.id.text2});
        listView.setAdapter(adapter);
    }
}
