package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //private ArrayList<UserLogin> logins = new ArrayList<UserLogin>();
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button buttonSignIn;

    private class UserLogin {
        private String email;
        private String password;
/*
        public UserLogin(String email, String password) {
            this.email = email;
            this.password = password;
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        /*if( firebaseAuth.getCurrentUser() != null) {
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }*/


        //logins.add(new UserLogin("admin", "pokeman"));
        // createUserAndLogin("username", "password");
    }

    public void userLogin() {
        EditText username = findViewById(R.id.editText);
        EditText password = findViewById(R.id.editText2);
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        progressDialog.setMessage("Logging in...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(usernameString, passwordString)
            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        finish();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed, invalid email or password", Toast.LENGTH_SHORT).show();

                    }

                }
            });
    }

    public void playlistPage(View v) {

        /*if (authenticateLogin(username.getText().toString(), password.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }*/
    }
/*
    public boolean createUserAndLogin(String username, String password) {
        return register(username, password) && authenticateLogin(username, password);
    } */
/*
    public boolean register(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (password.length() < 6 || password.length() > 16) {
            return false;
        }

        Pattern badChars = Pattern.compile("[~#*+%{}<>]");
        Pattern goodChars = Pattern.compile("[@.]");
        Matcher badMatcher = badChars.matcher(username);
        Matcher goodMatcher = goodChars.matcher(username);
        if (badMatcher.find() || !goodMatcher.find())
            return false;

        logins.add(new UserLogin(username, password));
        //return username.equals("jkurtz678@gmail.com") && password.equals("12345");
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
    }*/


    public void dialogueBoxRegister(View v) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Register");

        final EditText inputEmail = new EditText(this);
        inputEmail.setHint("Email");
        layout.addView(inputEmail);
        final EditText inputPasscode = new EditText(this);
        inputPasscode.setHint("Password");
        layout.addView(inputPasscode);
        final EditText inputPasscode2 = new EditText(this);
        inputPasscode2.setHint("Password");
        layout.addView(inputPasscode2);

        inputEmail.setInputType(InputType.TYPE_CLASS_TEXT);
        inputPasscode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        inputPasscode2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setView(layout);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = inputEmail.getText().toString();
                String pass = inputPasscode.getText().toString();
                String pass2 = inputPasscode2.getText().toString();

                if (!pass.equals(pass2)) {
                    Toast.makeText(LoginActivity.this, "Registration failed, passwords don't match", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else if (pass.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Registration failed, passwords must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                } else {

                    progressDialog.setMessage("Registering User...");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //user successfully registers
                                    Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.hide();


                                } else {
                                    //user fails to register
                                    Toast.makeText(LoginActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    progressDialog.hide();


                                }
                            }
                        });
                }
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

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin();
        }
    }

}
