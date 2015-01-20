package hecosire.com.hecosireapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.InputStream;

public class NewReportTask extends AsyncTask<Integer, Void, String> {

    private Exception exception;
    private Context context;
    private UserToken token;

    public NewReportTask(Context context, UserToken token) {
        this.context = context;
        this.token = token;
    }

    protected String doInBackground(Integer... values) {
        InputStream inputStream = null;

        try {

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost(MyApplication.RECORDS_API_URL);

            httppost.setEntity(new StringEntity("{ \"record\": { \"health_state_id\": "+ values[0] +" }}"));

            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("X-User-Token", token.getToken());
            httppost.setHeader("X-User-Email", token.getEmail());


            HttpResponse response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() != 201) {
                throw new Exception("Post failed");
            }

            return "";

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

    protected void onPostExecute(String result) {

        Intent goToNextActivity = new Intent(context.getApplicationContext(), MainActivity.class);
        goToNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(goToNextActivity);

    }
}