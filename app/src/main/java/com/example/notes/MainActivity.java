package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText login_email;
    private EditText login_password;
    private RelativeLayout login_btn;
    private RelativeLayout goto_signup_btn;
    private TextView goto_forgot_password;
    private ProgressBar main_progressbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_btn = findViewById(R.id.login_btn);
        goto_signup_btn = findViewById(R.id.goto_signup_btn);
        goto_forgot_password = findViewById(R.id.goto_forgot_password);
        main_progressbar = findViewById(R.id.main_progressbar);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {
            finish();
            callNextActivity(NotesActivity.class);
        }


        goto_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextActivity(SignUp.class);
            }
        });

        goto_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextActivity(ForgotPassword.class);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty()) {
                    displayToast("Please Fill All the Fields");
                } else {
                    // Login the User if the Fields are correct.
                    main_progressbar.setVisibility(View.VISIBLE);
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                checkMailVerification();
                            } else {
                                main_progressbar.setVisibility(View.INVISIBLE);
                                displayToast("Account Doesn't Exist.");
                            }
                        }
                    });

                }
            }
        });
    }

    private void checkMailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()) {
            displayToast("Logged In");
            finish();
            callNextActivity(NotesActivity.class);
        } else {
            main_progressbar.setVisibility(View.INVISIBLE);
            displayToast("Verify the Mail First");
            firebaseAuth.signOut();
        }
    }

    void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void callNextActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(MainActivity.this, destinationActivity);
        startActivity(intent);
    }
}