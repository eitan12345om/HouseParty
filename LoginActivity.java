package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

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

        logins.add(new UserLogin("admin", "pokeman"));
        // createUserAndLogin("username", "password");
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

    public void dialogueBox_Register(View v) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Register");

        final EditText inputTitle = new EditText(this);
        inputTitle.setHint("Email");
        layout.addView(inputTitle);
        final EditText inputPasscode = new EditText(this);
        inputPasscode.setHint("Password");
        layout.addView(inputPasscode);
        final EditText inputPasscode2 = new EditText(this);
        inputPasscode2.setHint("Password");
        layout.addView(inputPasscode2);


        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        inputPasscode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPasscode2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setView(layout);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String playlist_name = inputTitle.getText().toString();
                String pass = inputPasscode.getText().toString();
                String pass2 = inputPasscode2.getText().toString();
                dialog.cancel();

                /*if (!pass.equals(pass2)) {
                    dialog.cancel();
                }

                *** Authentification Shenanigans ***

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("CLIENT_ID", CLIENT_ID);
                intent.putExtra("REDIRECT_URI", REDIRECT_URI);
                intent.putExtra("REQUEST_CODE", REQUEST_CODE);
                startActivity(intent);*/
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
