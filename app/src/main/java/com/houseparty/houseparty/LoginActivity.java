package com.houseparty.houseparty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ArrayList<UserLogin> logins = new ArrayList<UserLogin>();

    private class UserLogin {
        private String email;
        private String password;

        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        createUserAndLogin("username", "password");
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

    public boolean createUserAndLogin(String username, String password) {
        return register(username, password) && authenticateLogin(username, password);
    }

    public boolean register(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (password.length() < 5 || password.length() > 16) {
            return false;
        }

        Pattern badChars = Pattern.compile("[~#*+%{}<>]");
        Pattern goodChars = Pattern.compile("[@.]");
        Matcher badMatcher = badChars.matcher(username);
        Matcher goodMatcher = goodChars.matcher(username);
        if (badMatcher.find() || !goodMatcher.find())
            return false;

        logins.add(new UserLogin(username, password));
        return username.equals("jkurtz678@gmail.com") && password.equals("12345");
    }

    public boolean authenticateLogin(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        for (UserLogin login : logins) {
            if (login.email.equals(username) && login.password.equals(password))
                return true;
        }
        return false;
    }
}
