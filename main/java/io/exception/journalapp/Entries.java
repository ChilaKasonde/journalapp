package io.exception.journalapp;

/**
 * Created by C. Kasonde on 7/1/2018.
 */
public class Entries extends EntryID{
    private String title, entry, date;

    public Entries(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
