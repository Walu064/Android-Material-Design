package com.example.Ticket_booking_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteId;
    @Column(unique = true)
    private String noteTitle;
    private String noteOwner;
    private String noteContent;
}
