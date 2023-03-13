package com.example.Ticket_booking_app.controller;

import com.example.Ticket_booking_app.model.Note;
import com.example.Ticket_booking_app.repository.NoteRepository;
import com.example.Ticket_booking_app.service.NoteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {
    @Autowired
    private NoteService noteService;
    private NoteRepository noteRepository;


    @GetMapping("/note/{noteOwner}")
    public ResponseEntity<List<Note>> getNotes(@PathVariable String noteOwner) {
        List<Note> notes = noteService.getNotesByOwner(noteOwner);
        if (notes != null) {
            System.out.println("GET: Notes by owner.");
            return new ResponseEntity<>(notes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/note/add")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote= noteService.addNote(note);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("note/delete/{noteTitle}")
    public ResponseEntity<Void> deleteNote(@PathVariable String noteTitle) {
        boolean success = noteService.deleteNote(noteTitle);
        if (success) {
            System.out.println("DELETE: Note "+noteTitle+" deleted.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PutMapping("/note/update/{noteTitle}")
    public ResponseEntity<Note> updateNote(@PathVariable(value = "noteTitle") String noteTitle,
                                           @RequestBody Note updatedNote) {
        Note note = noteService.findByNoteTitle(noteTitle);
        note.setNoteTitle(updatedNote.getNoteTitle());
        note.setNoteContent(updatedNote.getNoteContent());
        final Note newUpdatedNote = noteService.addNote(note);
        System.out.println("PUT: "+noteTitle+" updated.");
        return ResponseEntity.ok(newUpdatedNote);
    }
}