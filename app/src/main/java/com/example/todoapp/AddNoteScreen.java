package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddNoteScreen extends AppCompatActivity {
    private EditText noteText;
    private RadioButton low;
    private RadioButton medium;
    private Button button;
    private AddNoteScreenViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_screen);
        initViews();

        viewModel.getShouldClose().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose) {
                    finish();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }
    private void saveNote() {
        String text = noteText.getText().toString().trim();
        int priority = getPriority();
        Note newNote = new Note(0, text, priority);
        viewModel.saveNote(newNote);
    }
    private int getPriority() {
        int priority;
        if (low.isChecked()) {
            priority = 0;
        } else if (medium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        viewModel = new ViewModelProvider(this).get(AddNoteScreenViewModel.class);
        noteText = findViewById(R.id.editTextNoteText);
        button = findViewById(R.id.buttonAddNote);
        low = findViewById(R.id.radioButtonLow);
        medium = findViewById(R.id.radioButtonMedium);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteScreen.class);
    }
}