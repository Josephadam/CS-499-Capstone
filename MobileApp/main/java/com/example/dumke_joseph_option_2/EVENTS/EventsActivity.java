package com.example.dumke_joseph_option_2.EVENTS;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dumke_joseph_option_2.DATA.EventDBHelper;
import com.example.dumke_joseph_option_2.R;
import com.example.dumke_joseph_option_2.SMS.SmsSettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventAdapter adapter;
    List<Event> eventList;
    EventDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);  // link to activity_events.xml

        // 1. Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerEvents);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        dbHelper = new EventDBHelper(this);
        eventList = new ArrayList<>(dbHelper.getAllEvents());

        adapter = new EventAdapter(eventList);
        recyclerView.setAdapter(adapter);

        // 2. Setup FloatingActionButton INSIDE onCreate
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            // Test toast to make sure click works
             Toast.makeText(this, "FAB clicked!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NewEventActivity.class));
        });

        // SMS BUTTON
        Button smsBtn = findViewById(R.id.buttonSmsSettings);
        // In case it’s visually overlapping with the RecyclerView:
        smsBtn.bringToFront();
        smsBtn.setOnClickListener(v -> {
            Toast.makeText(this, "Opening SMS Settings…", Toast.LENGTH_SHORT).show(); // debug
            startActivity(new Intent(this, SmsSettingsActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh list when returning
        eventList.clear();
        eventList.addAll(dbHelper.getAllEvents());
        adapter.notifyDataSetChanged();
    }
}
