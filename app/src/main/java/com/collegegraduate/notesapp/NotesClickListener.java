package com.collegegraduate.notesapp;

import android.view.View;

interface NotesClickListener {
    void onClick(Notes notes);

    void onLongClick(Notes notes, View view);
}
