package com.example.androidmaterialdesign;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText noteTitleEditText;
    private TextInputEditText noteDescriptionEditText;
    private final List<Note> notesList = new ArrayList<Note>();
    private NotesAdapter notesAdapter;

    private String actualLoggedUser;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        noteTitleEditText = findViewById(R.id.note_title);
        noteDescriptionEditText = findViewById(R.id.note_description);
        FloatingActionButton addNoteFab = findViewById(R.id.add_note_fab);
        RecyclerView notesRecyclerView = findViewById(R.id.notes_recycler_view);

        notesAdapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(notesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        actualLoggedUser = getIntent().getExtras().getString("loggedUserEmail");
        TextView textViewWelcomeUsername = findViewById(R.id.textView_welcomeUsername);
        textViewWelcomeUsername.setText("Welcome, "+actualLoggedUser+"!");
        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String title = Objects.requireNonNull(noteTitleEditText.getText()).toString();
                String description = Objects.requireNonNull(noteDescriptionEditText.getText()).toString();

                if (!title.isEmpty() && !description.isEmpty()) {
                    Note note = new Note(actualLoggedUser, title, description);
                    notesList.add(note);
                    notesAdapter.notifyDataSetChanged();

                    noteTitleEditText.setText("");
                    noteDescriptionEditText.setText("");
                    Toast.makeText(MainActivity.this, "Note added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both title and description!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}