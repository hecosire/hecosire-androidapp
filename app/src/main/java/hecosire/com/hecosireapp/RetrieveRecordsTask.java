package hecosire.com.hecosireapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RetrieveRecordsTask extends AsyncTask<String, Void, String> {

    private Exception exception;
    private MainActivity activity;
    private UserToken token;

    public RetrieveRecordsTask(MainActivity activity, UserToken token) {

        this.activity = activity;
        this.token = token;
    }

    protected String doInBackground(String... urls) {
        DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
        HttpGet httppost = new HttpGet("http://hecosire.com/api/v1/records");
// Depends on your web service
        httppost.setHeader("Content-type", "application/json");
        httppost.setHeader("Accept", "application/json");
        httppost.setHeader("X-User-Token", token.getToken());
        httppost.setHeader("X-User-Email", token.getEmail());

        InputStream inputStream = null;
        String result = null;
        try {
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            return result;

        } catch (Exception e) {
            this.exception = e;
            Log.e("test", "test", e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception squish) {
                Log.e("test", "test", squish);
                this.exception = squish;
            }
        }
        return null;


    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        Toast.makeText(activity, "test" + feed, Toast.LENGTH_LONG).show();
    }
}