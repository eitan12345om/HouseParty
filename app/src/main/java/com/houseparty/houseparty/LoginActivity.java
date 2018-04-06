package com.houseparty.houseparty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void playlistPage(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public Boolean register( String username, String password ){
        if( username == null || password == null )
            return false;
        return username.equals( "jkurtz678@gmail.com" ) && password.equals(  "1234" );
    }
}
