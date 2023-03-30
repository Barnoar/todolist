package com.example.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final NoteDatabase noteDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getInstance(application);
    }

    public void remove(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.notesDao().remove(note.getId());
            }
        });
        thread.start();
    }

    public LiveData<List<Note>> getNotes() {
        return noteDatabase.notesDao().getNotes();
    }

}
