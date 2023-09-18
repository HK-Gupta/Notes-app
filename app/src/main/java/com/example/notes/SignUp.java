package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText signup_email;
    private EditText signup_password;
    private RelativeLayout signup_btn;
    private TextView goto_login;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign Up");

        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_btn = findViewById(R.id.signup_btn);
        goto_login = findViewById(R.id.goto_login);

        //This Variable is used for registering new user in the Database.
        firebaseAuth = FirebaseAuth.getInstance();

        goto_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextActivity(MainActivity.class);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()) {
                    displayToast("Please Fill All the Fields");
                } else if (password.length() < 7) {
                    displayToast("Password Should Contain At Least 7 characters");
                } else {
                    // Register the user to Fire Base. As all the fields are filled correctly.
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                displayToast("Registration Successful");
                                sendEmailVerification();
                            } else {
                                displayToast("Failed To Register");
                            }
                        }
                    });
                }
            }
        });
    }
    void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    //Send Email Verification.
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    displayToast("Verification Email is Sent, Verify is and Log In");
                    firebaseAuth.signOut();
                    callNextActivity(MainActivity.class);
                }
            });
        } else {
            displayToast("Failed to Send Verification Email");
        }
    }
    private void callNextActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(SignUp.this, destinationActivity);
        startActivity(intent);
    }
}