package com.example.dumke_joseph_option_2.DATA;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dumke_joseph_option_2.EVENTS.Event;

import java.util.ArrayList;
import java.util.List;

public class EventDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EventsDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EVENTS = "events";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";
    public static final String COL_NOTES = "notes";
    public static final String COL_REMIND = "remind";

    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_EVENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITLE + " TEXT, " +
                COL_DATE + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_NOTES + " TEXT, " +
                COL_REMIND + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // CREATE
    public boolean insertEvent(String title, String date, String time, String notes, boolean remind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_NOTES, notes);
        values.put(COL_REMIND, remind ? 1 : 0);

        long result = db.insert(TABLE_EVENTS, null, values);
        return result != -1;
    }

    // READ
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);

        if (cursor.moveToFirst()) {
            do {
                events.add(new Event(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE)) + " " +
                                cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_REMIND)) == 1
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }

    // UPDATE
    public boolean updateEvent(int id, String title, String date, String time, String notes, boolean remind) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_DATE, date);
        values.put(COL_TIME, time);
        values.put(COL_NOTES, notes);
        values.put(COL_REMIND, remind ? 1 : 0);

        int rows = db.update(TABLE_EVENTS, values, COL_ID + "=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    // DELETE
    public boolean deleteEvent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_EVENTS, COL_ID + "=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }
}
