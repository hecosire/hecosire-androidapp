package hecosire.com.hecosireapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.iangclifton.android.floatlabel.FloatLabel;


public class LoginActivity extends Activity {

    private EditText emailEdit;
    private EditText passwordEdit;
    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        setContentView(R.layout.activity_login);

        emailEdit = ((FloatLabel)findViewById(R.id.emailText)).getEditText();
        emailEdit.setTextColor(getResources().getColor(R.color.smart_watch_2_text_color_white));
        passwordEdit =  ((FloatLabel)findViewById(R.id.passwordText)).getEditText();
        passwordEdit.setTextColor(getResources().getColor(R.color.smart_watch_2_text_color_white));
        loginButton = findViewById(R.id.button2);


        setTitle("Please login");

        ((MyApplication)getApplication()).reportScreenView("Login view");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void performLogin(View view) {
       String email = emailEdit.getText().toString();
       String password = passwordEdit.getText().toString();
       if (!email.isEmpty() && !password.isEmpty()) {
           loginButton.setEnabled(false);
           new LoginTask(this, email, password).execute();
       }

    }


    public void createNewAccount(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://hecosire.com/users/sign_up"));
        startActivity(browserIntent);
    }

    public void loginSuccessful(String email, String token) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.emailPreferenceKey), email);
        editor.putString(getString(R.string.tokenPreferenceKey), token);
        editor.apply();

        Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToNextActivity);

    }


    public void loginFailed() {
        loginButton.setEnabled(true);
        Toast.makeText(this, "There was a problem with logging in..", Toast.LENGTH_LONG).show();
    }
}
