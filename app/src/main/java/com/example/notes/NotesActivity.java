package com.example.notes;

import static com.example.notes.R.id.logOut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class NotesActivity extends AppCompatActivity {

    private FloatingActionButton create_note_fab;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("All Notes");

        firebaseAuth = FirebaseAuth.getInstance();
        create_note_fab = findViewById(R.id.create_note_fab);
        create_note_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callNextActivity(CreateNote.class);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        firebaseAuth.signOut();
        finish();
        callNextActivity(MainActivity.class);
        return super.onOptionsItemSelected(item);
    }

    private void callNextActivity(Class<?> destinationActivity) {
        Intent intent = new Intent(NotesActivity.this, destinationActivity);
        startActivity(intent);
    }
}