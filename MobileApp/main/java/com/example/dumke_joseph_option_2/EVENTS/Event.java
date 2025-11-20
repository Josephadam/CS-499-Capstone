package com.example.dumke_joseph_option_2.EVENTS;

public class Event {
    private int id;
    private String title;
    private String due;
    private String notes;
    private boolean remind;

    // Constructor for creating events
    public Event(int id, String title, String due, String notes, boolean remind) {
        this.id = id;
        this.title = title;
        this.due = due;
        this.notes = notes;
        this.remind = remind;
    }

    // Simpler constructor if you don’t have ID yet
    public Event(String title, String due) {
        this.title = title;
        this.due = due;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDue() {
        return due;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isRemind() {
        return remind;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }
}
