package com.example.Ticket_booking_app.service;

import com.example.Ticket_booking_app.model.Note;
import com.example.Ticket_booking_app.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getNotesByOwner(String noteOwner){
        return noteRepository.findByNoteOwner(noteOwner);
    }

    public Note addNote(Note note){
        return noteRepository.save(note);
    }

    public Note findByNoteTitle(String noteTitle) {
        return noteRepository.findByNoteTitle(noteTitle);
    }

    public boolean deleteNote(String noteTitle) {
        Note note = noteRepository.findByNoteTitle(noteTitle);
        if (note != null) {
            noteRepository.delete(note);
            return true;
        } else {
            return false;
        }
    }


}
