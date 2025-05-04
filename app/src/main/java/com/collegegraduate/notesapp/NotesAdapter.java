package com.collegegraduate.notesapp;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    Context context;
    List<Notes> list;
    NotesClickListener listener;

    public NotesAdapter(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Notes note = list.get(position);

        holder.textView_title.setText(note.getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(note.getNotes());

        holder.textView_date.setText(note.getDate());
        holder.textView_date.setSelected(true);

        if (note.isPinned()) {
            holder.imageView_pin.setImageResource(R.drawable.ic_pin);
        } else {
            holder.imageView_pin.setImageResource(0);
        }

        try {
            holder.notes_container.setCardBackgroundColor(Color.parseColor(note.getColor()));
        } catch (Exception e) {
            // Default to a dark background if color parsing fails
            holder.notes_container.setCardBackgroundColor(Color.parseColor("#1F1F1F"));
        }

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(note);
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(note, v);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        CardView notes_container;
        TextView textView_title, textView_notes, textView_date;
        ImageView imageView_pin;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notes_container = itemView.findViewById(R.id.notes_container);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_notes = itemView.findViewById(R.id.textView_notes);
            textView_date = itemView.findViewById(R.id.textView_date);
            imageView_pin = itemView.findViewById(R.id.imageView_pin);
        }
    }
}