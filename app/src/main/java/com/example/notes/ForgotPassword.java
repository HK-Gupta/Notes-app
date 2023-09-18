package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgot_password;
    private Button btn_password_recover;
    private TextView goto_login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        forgot_password = findViewById(R.id.forgot_password);
        btn_password_recover = findViewById(R.id.btn_password_recover);
        goto_login_password = findViewById(R.id.goto_login_password);

        goto_login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_password_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgot_password.getText().toString().trim();
                if(email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the mail id first", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Send Mail to recover Password.
                }
            }
        });
    }
}