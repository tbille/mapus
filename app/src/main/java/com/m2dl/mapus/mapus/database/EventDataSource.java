package com.m2dl.mapus.mapus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.m2dl.mapus.mapus.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventDataSource {

    private SQLiteDatabase database;
    private Database db;
    private String[] allColumns = {
            db.COLUMN_ID,
            db.COLUMN_DAY,
            db.COLUMN_PRETTY_TIME,
            db.COLUMN_START_TIME,
            db.COLUMN_END_TIME,
            db.COLUMN_CATEGORIE,
            db.COLUMN_PRETTY_WEEK,
            db.COLUMN_WEEK_NUMBER,
            db.COLUMN_MATIERE,
            db.COLUMN_GROUP,
            db.COLUMN_SALLE,
            db.COLUMN_NOTES
    };

    public EventDataSource(Context context) {
        db = new Database(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void createEvent(Event event) {
        ContentValues values = new ContentValues();

        values.put(Database.COLUMN_DAY, event.getDay());
        values.put(Database.COLUMN_PRETTY_TIME, event.getPrettyTime());
        values.put(Database.COLUMN_START_TIME, event.getStartTime());
        values.put(Database.COLUMN_END_TIME, event.getEndTime());
        values.put(Database.COLUMN_CATEGORIE, event.getCategorie());
        values.put(Database.COLUMN_PRETTY_WEEK, event.getPrettyWeek());
        values.put(Database.COLUMN_WEEK_NUMBER, event.getWeekNumber());
        values.put(Database.COLUMN_MATIERE, event.getMatiere());
        values.put(Database.COLUMN_GROUP, event.getGroup());
        values.put(Database.COLUMN_SALLE, event.getSalle());
        values.put(Database.COLUMN_NOTES, event.getNotes());

        long insertId = database.insert(
                Database.TABLE_EVENTS,
                null,
                values
        );
    }

    public void addEventList(List<Event> events) {
        for (Event event :
                events) {
            createEvent(event);
        }
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<Event>();

        Cursor cursor = database.query(
                Database.TABLE_EVENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }

        cursor.close();
        return events;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setDay(cursor.getInt(1));
        event.setPrettyTime(cursor.getString(2));
        event.setStartTime(cursor.getString(3));
        event.setEndTime(cursor.getString(4));
        event.setCategorie(cursor.getString(5));
        event.setPrettyWeek(cursor.getString(6));
        event.setWeekNumber(cursor.getInt(7));
        event.setMatiere(cursor.getString(8));
        event.setGroup(cursor.getString(9));
        event.setSalle(cursor.getString(10));
        event.setNotes(cursor.getString(11));

        return event;
    }

    public ArrayList<Event> getEventByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) - 2) % 7;

        String whereClause = db.COLUMN_WEEK_NUMBER + " = " + String.valueOf(week)
                +" AND "+ db.COLUMN_DAY + " = " + String.valueOf(dayOfWeek);

        Cursor cursor = database.query(
                Database.TABLE_EVENTS,
                allColumns, whereClause, null, null, null, null);

        ArrayList<Event> events = new ArrayList<Event>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Event event = cursorToEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }

        return events;
    }

    public void createDatabase() {
        db.createDatabaseEvents(database);
    }

    public void deleteDatabase() {
        db.removeDatabaseEvents(database);
    }
}
