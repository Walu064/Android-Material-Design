package com.example.Ticket_booking_app.repository;

import com.example.Ticket_booking_app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByNoteOwner(String noteOwner);
    Note findByNoteTitle(String noteTitle);
}
