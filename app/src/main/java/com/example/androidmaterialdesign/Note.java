package com.example.androidmaterialdesign;

public class Note {

    private final String ownerEmail;
    private final String title;
    private final String description;

    public Note(String ownerEmail, String title, String description) {
        this.ownerEmail = ownerEmail;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }
}
