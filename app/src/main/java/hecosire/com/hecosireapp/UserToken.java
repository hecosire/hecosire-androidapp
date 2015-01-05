package hecosire.com.hecosireapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class UserToken {

    private String email;
    private String token;

    public static UserToken getUserToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String email = sharedPref.getString(context.getString(R.string.emailPreferenceKey), "MISSING");
        String token = sharedPref.getString(context.getString(R.string.tokenPreferenceKey), "MISSING");

        if ("MISSING".equals(email) || "MISSING".equals(token)) {
            Intent goToNextActivity = new Intent(context.getApplicationContext(), LoginActivity.class);
            context.startActivity(goToNextActivity);
            return null;
        }

        return new UserToken(email, token);
    };

    public UserToken(String email, String token) {

        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
