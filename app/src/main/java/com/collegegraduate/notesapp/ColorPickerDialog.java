package com.collegegraduate.notesapp;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


public class ColorPickerDialog {

    private Context context;
    private Dialog dialog;
    private Notes selectedNote;
    private OnColorSelectedListener listener;

    // Available dark colors
    private final String[] colors = {
            "#2D2D2D", // Default Dark
            "#412A48", // Dark Purple
            "#2A344A", // Dark Blue
            "#3B392E", // Dark Yellow
            "#2D3B2E", // Dark Green
            "#3B2E2E"  // Dark Red
    };

    public interface OnColorSelectedListener {
        void onColorSelected(String color);
    }

    public ColorPickerDialog(Context context, Notes selectedNote, OnColorSelectedListener listener) {
        this.context = context;
        this.selectedNote = selectedNote;
        this.listener = listener;
        createDialog();
    }

    private void createDialog() {
        dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.color_picker_dialog, null);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        GridLayout gridLayout = view.findViewById(R.id.color_grid);

        // Setup grid with color options
        for (String color : colors) {
            CardView colorCard = createColorCard(color);
            gridLayout.addView(colorCard);
        }

        dialog.setCancelable(true);
    }

    private CardView createColorCard(final String colorHex) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 8, 8, 8);

        CardView cardView = new CardView(context);
        cardView.setLayoutParams(params);
        cardView.setCardBackgroundColor(Color.parseColor(colorHex));
        cardView.setRadius(18);
        cardView.setCardElevation(4);

        // Create a checkmark image view for selected color
        ImageView checkmark = new ImageView(context);
        checkmark.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        checkmark.setImageResource(R.drawable.ic_check);
        checkmark.setPadding(24, 24, 24, 24);
        checkmark.setVisibility(selectedNote != null && selectedNote.getColor().equals(colorHex) ?
                View.VISIBLE : View.INVISIBLE);

        cardView.addView(checkmark);

        // Set click listener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onColorSelected(colorHex);
                    dialog.dismiss();
                }
            }
        });

        return cardView;
    }

    public void show() {
        dialog.show();
    }
}
