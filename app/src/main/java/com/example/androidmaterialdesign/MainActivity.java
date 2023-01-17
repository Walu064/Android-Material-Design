package com.example.androidmaterialdesign;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

//TODO: Convert app to MVVM architecture
public class MainActivity extends AppCompatActivity {

    private TextInputEditText noteTitleEditText;
    private TextInputEditText noteDescriptionEditText;

    private FloatingActionButton floatingActionButtonAdd;
    private FloatingActionButton floatingActionButtonRefresh;
    private FloatingActionButton floatingActionButtonProfile;

    private String actualLoggedUser;
    private final List<Note> notesList = new ArrayList<Note>();
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);
        initFrontComponents();
    }

    @SuppressLint("SetTextI18n")
    private void initFrontComponents(){
        noteTitleEditText = findViewById(R.id.note_title);
        noteDescriptionEditText = findViewById(R.id.note_description);
        floatingActionButtonAdd = findViewById(R.id.add_note_fab);
        floatingActionButtonRefresh = findViewById(R.id.refresh_fab);
        floatingActionButtonProfile = findViewById(R.id.floatingActionButton_profile);
        RecyclerView notesRecyclerView = findViewById(R.id.notes_recycler_view);

        notesAdapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(notesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        actualLoggedUser = getIntent().getExtras().getString("loggedUserEmail");
        TextView textViewWelcomeUsername = findViewById(R.id.textView_welcomeUsername);
        textViewWelcomeUsername.setText("Welcome, "+actualLoggedUser+"!");
        initListeners();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initListeners(){
        floatingActionButtonAdd.setOnClickListener(view-> {
            String description = Objects.requireNonNull(noteDescriptionEditText.getText()).toString();
            String title = Objects.requireNonNull(noteTitleEditText.getText()).toString();
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
        });
        floatingActionButtonRefresh.setOnClickListener(view->{
            //TODO: Listener - refresh method with api sync
        });
        floatingActionButtonProfile.setOnClickListener(view->{
            //TODO: Listener - side-fragment with profile informations
        });

    }
}