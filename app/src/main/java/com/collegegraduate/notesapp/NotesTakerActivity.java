package com.collegegraduate.notesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editTitle, editNotes;
    ImageView imageSave;
    Notes notes;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editTitle = findViewById(R.id.edit_title);
        editNotes = findViewById(R.id.edit_notes);
        imageSave = findViewById(R.id.image_save);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("note");
            editTitle.setText(notes.getTitle());
            editNotes.setText(notes.getNotes());
            isOldNote = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String description = editNotes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Please add some notes!", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a", Locale.getDefault());
                Date date = new Date();

                if (!isOldNote) {
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setDate(formatter.format(date));

                RoomDB database = RoomDB.getInstance(NotesTakerActivity.this);
                if (isOldNote) {
                    database.mainDAO().update(notes);
                } else {
                    database.mainDAO().insert(notes);
                }
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Check if content was modified and ask if user wants to save
        // This is a simplified version
        finish();
    }

    public static void newInstance(Activity activity) {
        Intent intent = new Intent(activity, NotesTakerActivity.class);
        activity.startActivity(intent);
    }

    public static void newInstance(Activity activity, Notes note) {
        Intent intent = new Intent(activity, NotesTakerActivity.class);
        intent.putExtra("note", note);
        activity.startActivity(intent);
    }
}