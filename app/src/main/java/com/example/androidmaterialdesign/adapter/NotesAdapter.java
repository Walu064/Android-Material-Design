package com.example.androidmaterialdesign.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmaterialdesign.R;
import com.example.androidmaterialdesign.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final List<Note> notesList;

    public NotesAdapter(List<Note> notesList) {
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.noteTitleTextView.setText(note.getNoteTitle());
        holder.noteContentTextView.setText(note.getNoteContent());
        holder.floatingActionButtonDeleteNote.setOnClickListener(view->{
            sendRequestDeleteNote(position);
            notesList.remove(position);
            notifyDataSetChanged();
        });
        holder.floatingActionButtonEditNote.setOnClickListener(view->{
            holder.editTextNewTitle.setVisibility(View.VISIBLE);
            holder.editTextNewContent.setVisibility(View.VISIBLE);
            holder.floatingActionButtonAcceptChanges.setVisibility(View.VISIBLE);

            holder.floatingActionButtonAcceptChanges.setOnClickListener(itemView->{
                sendRequestUpdateNote(holder, position);
                holder.editTextNewTitle.setVisibility(View.INVISIBLE);
                holder.editTextNewContent.setVisibility(View.INVISIBLE);
                holder.floatingActionButtonAcceptChanges.setVisibility(View.INVISIBLE);
            });
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void sendRequestDeleteNote(int position){
        OkHttpClient client = new OkHttpClient();
        String titleToDelete = notesList.get(position).getNoteTitle();
        String requestUrl = "http://192.168.0.103:8080/note/delete/"+titleToDelete;
        Request request = new Request.Builder().url(requestUrl).delete().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("REST RESPONSE ", response.toString());
                } else {
                    Log.e("ERROR ", response.toString());
                }
            }
        });
    }

    private void sendRequestUpdateNote(@NonNull NoteViewHolder holder, int position){
        OkHttpClient client = new OkHttpClient();
        String requestUrl = "http://192.168.0.103:8080/note/update/"+notesList.get(position).getNoteTitle();
        String editedTitle = holder.editTextNewTitle.getText().toString();
        String editedContent = holder.editTextNewContent.getText().toString();
        notesList.get(position).setNoteTitle(editedTitle);
        notesList.get(position).setNoteContent(editedContent);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                "{\"noteTitle\" : \""+notesList.get(position).getNoteTitle() +"\"," +
                        " \"noteOwner\" : \""+notesList.get(position).getNoteOwner()+"\"," +
                        " \"noteContent\" : \""+notesList.get(position).getNoteContent()+"\"}");
        Request request = new Request.Builder().url(requestUrl).put(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("REST RESPONSE ", response.toString());
                } else {
                    Log.e("ERROR ", response.toString());
                }
            }
        });
        holder.noteTitleTextView.setText(notesList.get(position).getNoteTitle());
        holder.noteContentTextView.setText(notesList.get(position).getNoteContent());
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitleTextView;
        TextView noteContentTextView;

        EditText editTextNewTitle;
        EditText editTextNewContent;

        FloatingActionButton floatingActionButtonDeleteNote;
        FloatingActionButton floatingActionButtonEditNote;
        FloatingActionButton floatingActionButtonAcceptChanges;


        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitleTextView = itemView.findViewById(R.id.note_title);
            noteContentTextView = itemView.findViewById(R.id.note_description);
            floatingActionButtonDeleteNote = itemView.findViewById(R.id.floatingActionButton_deleteNote);
            floatingActionButtonEditNote = itemView.findViewById(R.id.floatingActionButton_editNote);
            floatingActionButtonAcceptChanges = itemView.findViewById(R.id.floatingActionButton_acceptChanges);
            editTextNewTitle = itemView.findViewById(R.id.newTitle);
            editTextNewContent = itemView.findViewById(R.id.newContent);

            editTextNewTitle.setVisibility(View.INVISIBLE);
            editTextNewContent.setVisibility(View.INVISIBLE);
            floatingActionButtonAcceptChanges.setVisibility(View.INVISIBLE);
        }
    }
}
