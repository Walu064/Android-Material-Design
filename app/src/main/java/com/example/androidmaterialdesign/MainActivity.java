package com.example.androidmaterialdesign;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidmaterialdesign.adapter.NotesAdapter;
import com.example.androidmaterialdesign.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText noteTitleEditText;
    private TextInputEditText noteDescriptionEditText;

    private FloatingActionButton floatingActionButtonAdd;
    private FloatingActionButton floatingActionButtonRefresh;

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
        RecyclerView notesRecyclerView = findViewById(R.id.notes_recycler_view);

        notesAdapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(notesAdapter);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        actualLoggedUser = getIntent().getExtras().getString("username");
        TextView textViewWelcomeUsername = findViewById(R.id.textView_welcomeUsername);
        textViewWelcomeUsername.setText("Welcome, "+actualLoggedUser+"!");
        initListeners();
        getNotesListFromDatabase();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initListeners(){
        floatingActionButtonAdd.setOnClickListener(view-> {
            String content = Objects.requireNonNull(noteDescriptionEditText.getText()).toString();
            String title = Objects.requireNonNull(noteTitleEditText.getText()).toString();
            if (!title.isEmpty() && !content.isEmpty()) {
                Note note = new Note(title, content, actualLoggedUser);
                String insertedDataAsJson = "{\'noteTitle\' : \'"+note.getNoteTitle() +"\'," +
                        " \'noteOwner\' : \'"+note.getNoteOwner()+"\'," +
                        " \'noteContent\' : \'"+note.getNoteContent()+"\'}";
                Log.e("REQUEST BODY ", insertedDataAsJson);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(insertedDataAsJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String requestUrl = "http://192.168.0.103:8080/note/add";
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.POST, requestUrl, jsonObject,
                        response-> Log.e("REST RESPONSE: ", response.toString()),
                        error-> Log.e("REST RESPONSE: ", error.toString())
                );
                requestQueue.add(objectRequest);

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
            getNotesListFromDatabase();
        });
    }

    private void getNotesListFromDatabase(){
        notesList.clear();
        String requestUrl = "http://192.168.0.103:8080/note/"+actualLoggedUser;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET, requestUrl, null,
                response -> {
                    Log.e("REST RESPONSE ", response.toString());
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Note>>(){}.getType();
                    List<Note> listOfNotesFromDb = gson.fromJson(response.toString(), listType);
                    notesList.addAll(listOfNotesFromDb);
                    notesAdapter.notifyDataSetChanged();
                },
                error ->{ Log.e("REST RESPONSE ", error.toString());
                }
        );
        requestQueue.add(objectRequest);
    }

    public void sendRequestDeleteNote(int position){
        String titleToDelete = notesList.get(position).getNoteTitle();
        String requestUrl = "http://192.168.0.103:8080/note/delete/"+titleToDelete;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.DELETE, requestUrl, null,
                response -> {
                    Log.e("REST RESPONSE ", response.toString());
                },
                error ->{ Log.e("REST RESPONSE ", error.toString());
                }
        );
        requestQueue.add(objectRequest);
    }
}