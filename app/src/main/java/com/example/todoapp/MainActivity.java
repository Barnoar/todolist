package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotesAdapter adapter;
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingButton;
    private TextView textEmptyList;
    private ItemTouchHelper itemTouchHelper;
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVars();
        recyclerViewNotes.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        floatingButtonClickListener();
        mainViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.isEmpty()) {
                    textEmptyList.setVisibility(View.VISIBLE);
                    recyclerViewNotes.setVisibility(View.INVISIBLE);
                } else {
                    textEmptyList.setVisibility(View.INVISIBLE);
                    recyclerViewNotes.setVisibility(View.VISIBLE);
                    adapter.setNotes(notes);
                }
            }
        });
    }

    private void floatingButtonClickListener() {
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteScreen.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private ItemTouchHelper itemTouchHelper() {

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }
                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = adapter.getNotes().get(position);
                        mainViewModel.remove(note);
                    }
                });
        return itemTouchHelper;
    }
    private void initVars() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        floatingButton = findViewById(R.id.addButton);
        textEmptyList = findViewById(R.id.textViewEmptyList);
        adapter = new NotesAdapter();
        itemTouchHelper = itemTouchHelper();
    }
}