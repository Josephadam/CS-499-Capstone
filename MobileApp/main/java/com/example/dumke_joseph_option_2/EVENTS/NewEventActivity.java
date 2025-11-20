package com.example.dumke_joseph_option_2.EVENTS;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dumke_joseph_option_2.DATA.EventDBHelper;
import com.example.dumke_joseph_option_2.R;

import java.util.Calendar;

public class NewEventActivity extends AppCompatActivity {

    EditText editTitle, editNotes;
    Switch switchRemind;
    Button buttonSave, buttonDelete, buttonPickDate, buttonPickTime;
    EventDBHelper dbHelper;

    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        dbHelper = new EventDBHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editNotes = findViewById(R.id.editNotes);
        switchRemind = findViewById(R.id.switchRemind);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonPickDate = findViewById(R.id.buttonPickDate);
        buttonPickTime = findViewById(R.id.buttonPickTime);

        Calendar calendar = Calendar.getInstance();

        // Pick Date
        buttonPickDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        selectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                        buttonPickDate.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Pick Time
        buttonPickTime.setOnClickListener(v -> {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    this,
                    (view, selectedHour, selectedMinute) -> {
                        selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        buttonPickTime.setText(selectedTime);
                    },
                    hour, minute, false
            );
            timePickerDialog.show();
        });

        // Save Event
        buttonSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString();
            String notes = editNotes.getText().toString();
            boolean remind = switchRemind.isChecked();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please pick a date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedTime.isEmpty()) {
                Toast.makeText(this, "Please pick a time", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = dbHelper.insertEvent(title, selectedDate, selectedTime, notes, remind);
            if (inserted) {
                Toast.makeText(this, "Event saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to save event", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete placeholder
        buttonDelete.setOnClickListener(v -> {
            Toast.makeText(this, "Delete not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }
}
