package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgot_password;
    private Button btn_password_recover;
    private TextView goto_login_password;

    private FirebaseAuth firebaseAuth;
    private ProgressBar forgot_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Forgot Password");

        forgot_password = findViewById(R.id.forgot_password);
        btn_password_recover = findViewById(R.id.btn_password_recover);
        goto_login_password = findViewById(R.id.goto_login_password);
        forgot_progressbar = findViewById(R.id.forgot_progressbar);

        firebaseAuth = FirebaseAuth.getInstance();
        goto_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextActivity(MainActivity.class);
            }
        });

        btn_password_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgot_password.getText().toString().trim();
                if(email.isEmpty()) {
                    displayToast("Enter the mail id first");
                }
                else {
                    // Send Mail to recover Password.
                    forgot_progressbar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                displayToast("Mail sent, Recover your Password using mail");
                                finish();
                                callNextActivity(MainActivity.class);
                            } else {
                                forgot_progressbar.setVisibility(View.INVISIBLE);
                                displayToast("Account Doesn't Exist !");
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
    private void callNextActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(ForgotPassword.this, destinationActivity);
        startActivity(intent);
    }
}