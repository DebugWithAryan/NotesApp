package com.collegegraduate.notesapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesClickListener, PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    List<Notes> notes = new ArrayList<>();
    RoomDB database;
    FloatingActionButton fabAdd;
    SearchView searchView;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fabAdd = findViewById(R.id.fab_add);
        searchView = findViewById(R.id.search_view);

        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesTakerActivity.newInstance(MainActivity.this);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes> filteredList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if (singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(singleNote);
            }
        }
        notesAdapter.filterList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notes = database.mainDAO().getAll();
        updateRecycler(notes);
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesAdapter = new NotesAdapter(MainActivity.this, notes, this);
        recyclerView.setAdapter(notesAdapter);
    }

    // Implementing NotesClickListener interface methods
    @Override
    public void onClick(Notes notes) {
        NotesTakerActivity.newInstance(MainActivity.this, notes);
    }

    @Override
    public void onLongClick(Notes notes, View view) {
        selectedNote = notes;
        showPopup(view);
    }

    private void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.pin) {
            if (selectedNote.isPinned()) {
                database.mainDAO().pin(selectedNote.getID(), false);
                Toast.makeText(MainActivity.this, "Unpinned!", Toast.LENGTH_SHORT).show();
            } else {
                database.mainDAO().pin(selectedNote.getID(), true);
                Toast.makeText(MainActivity.this, "Pinned!", Toast.LENGTH_SHORT).show();
            }
            notes = database.mainDAO().getAll();
            updateRecycler(notes);
            return true;
        } else if (itemId == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete Note");
            builder.setMessage("Are you sure you want to delete this note?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    database.mainDAO().delete(selectedNote);
                    notes.remove(selectedNote);
                    notesAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
            return true;
        }
        return false;
    }
}