package com.example.notes;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    @Test
    public void createNewNote() {
        DataManager instance = DataManager.getInstance();
        final CourseInfo course = instance.getCourse("android_async");
        final String noteTitle = "Test Note Title";
        final String noteText = "This is the body text of my text note";

        int noteIndex = instance.createNewNote();
        //Get note associated with index
        NoteInfo newNote = instance.getNotes().get(noteIndex);
        newNote.setCourse(course);
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = instance.getNotes().get(noteIndex);
        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());

    }

    @Test
    public void findSimilarNotes(){
        DataManager instance = DataManager.getInstance();
        final CourseInfo course = instance.getCourse("android_async");
        final String noteTitle = "Test Note Title";
        final String noteText1 = "This is the body text of my text note";
        final String noteText2 = "This is the body text of my text note";

        int noteIndex1 = instance.createNewNote();
        //Get note associated with index
        NoteInfo newNote1 = instance.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = instance.createNewNote();
        //Get note associated with index
        NoteInfo newNote2 = instance.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = instance.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = instance.findNote(newNote2);
        assertEquals(noteIndex2 , foundIndex2);

    }
}