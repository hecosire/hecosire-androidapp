package hecosire.com.hecosireapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LoginTask extends AsyncTask<String, Void, String[]> {

    private Exception exception;
    private LoginActivity activity;
    private String email;
    private String password;

    public LoginTask(LoginActivity activity, String email, String password) {

        this.activity = activity;
        this.email = email;
        this.password = password;
    }

    protected String[] doInBackground(String... urls) {
        InputStream inputStream = null;

        try {

            DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
            HttpPost httppost = new HttpPost("http://hecosire.com/api/v1/users/sign_in");

            httppost.setEntity(new StringEntity("{ \"user\": { \"email\":\""+ email +"\",\"password\":\"" +password+ "\"}}"));
            httppost.setHeader("Content-type", "application/json");
            httppost.setHeader("Accept", "application/json");

            String[] result = new String[2];
            HttpResponse response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new Exception("Login failed");
            }

            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            JSONObject jsonResponse = new JSONObject(sb.toString());

            result[0] =  jsonResponse.getString("email");
            result[1] =  jsonResponse.getString("auth_token");
            
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

    protected void onPostExecute(String[] result) {
        if (exception != null) {
            activity.loginFailed();
            return;
        }

        activity.loginSuccessful(result[0], result[1]);
    }
}