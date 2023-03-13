package com.example.androidmaterialdesign.model;

public class Note {

    private String noteTitle;
    private final String noteOwner;
    private String noteContent;

    public Note(String noteTitle, String noteContent, String noteOwner) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteOwner = noteOwner;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteOwner() {
        return noteOwner;
    }

    public void setNoteTitle(String noteTitle){
        this.noteTitle = noteTitle;
    }

    public void setNoteContent(String noteContent){
        this.noteContent = noteContent;
    }
}
