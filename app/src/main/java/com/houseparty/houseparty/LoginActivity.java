package com.houseparty.houseparty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void playlistPage(View v) {
        EditText username = (EditText) findViewById(R.id.editText);
        EditText password = (EditText) findViewById(R.id.editText2);
        if (authenticateLogin(username.getText().toString(), password.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean register(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return username.equals("jkurtz678@gmail.com") && password.equals("1234");
    }

    public boolean authenticateLogin(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        return username.equals("jkurtz678@gmail.com") && password.equals("1234");

        if (username.equals("matt@gmail.com")) {
            return false;
        }
        if (password.length() < 5 || password.length() > 16)
            return false;

        return username.equals("jkurtz678@gmail.com") && password.equals("1234");
    }
}
