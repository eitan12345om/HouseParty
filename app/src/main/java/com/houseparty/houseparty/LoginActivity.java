package com.houseparty.houseparty;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import static com.houseparty.houseparty.HousePartyPreferences.PREF_PASSWORD;
import static com.houseparty.houseparty.HousePartyPreferences.PREF_USERNAME;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button buttonSignIn;
    private boolean loginSuccess;
    private boolean loginResult = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

        if (username == null || password == null) {
            progressDialog = new ProgressDialog(this);
            firebaseAuth = FirebaseAuth.getInstance();
        } else {
            finish();
            Intent intent = new Intent(LoginActivity.this, PlaylistActivity.class);
            startActivity(intent);
        }
    }

    public boolean userLogin(final String usernameString, final String passwordString) {

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(usernameString, passwordString)
            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                            .edit()
                            .putString(PREF_USERNAME, usernameString)
                            .putString(PREF_PASSWORD, passwordString)
                            .apply();

                        finish();
                        Intent intent = new Intent(LoginActivity.this, PlaylistActivity.class);
                        startActivity(intent);
                        loginSuccess = true;


                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed, invalid email or password", Toast.LENGTH_SHORT).show();
                        loginSuccess = false;
                    }

                }
            });
        return loginSuccess;
    }

    public void playlistPage(View v) {
    }

    public void dialogueBoxRegister(View v) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Register");

        final EditText inputEmail = new EditText(this);
        inputEmail.setHint("Email");
        layout.addView(inputEmail);
        final EditText inputPasscode = new EditText(this);
        inputPasscode.setHint("Password(six characters minimum)");
        layout.addView(inputPasscode);
        final EditText inputPasscode2 = new EditText(this);
        inputPasscode2.setHint("Confirm password");
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

                Boolean exitCode = registerUser(email, pass, pass2 );

                if( exitCode ) {
                    dialog.cancel();
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

    public boolean registerUser( String email, String pass, String pass2) {
        if (!pass.equals(pass2)) {
            Toast.makeText(LoginActivity.this, "Registration failed, passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() < 6) {
            Toast.makeText(LoginActivity.this, "Registration failed, passwords must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
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
        return true;
    }

    public boolean testUserLogin() {
        userLogin();
        return true;
    }

    public boolean testUserLogin(String user, String pass) {

        firebaseAuth.signInWithEmailAndPassword(user, pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            loginResult = true;
                        } else {
                            loginResult = false;
                        }

                    }
                });

        return loginResult;

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            EditText username = findViewById(R.id.editText);
            EditText password = findViewById(R.id.editText2);
            final String usernameString = username.getText().toString();
            final String passwordString = password.getText().toString();
            userLogin(usernameString, passwordString);
        }
    }
}
