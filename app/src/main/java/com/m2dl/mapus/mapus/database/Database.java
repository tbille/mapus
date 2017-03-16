package com.m2dl.mapus.mapus.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mapus.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_ID = "_id";

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_PRETTY_TIME = "prettytime";
    public static final String COLUMN_START_TIME = "starttime";
    public static final String COLUMN_END_TIME = "endtime";
    public static final String COLUMN_CATEGORIE = "categorie";
    public static final String COLUMN_PRETTY_WEEK = "prettyweek";
    public static final String COLUMN_WEEK_NUMBER = "weeknumber";
    public static final String COLUMN_MATIERE = "matiere";
    public static final String COLUMN_GROUP = "groupe";
    public static final String COLUMN_SALLE = "salle";
    public static final String COLUMN_NOTES = "notes";

    private static final String DATABASE_CREATE_EVENT = "CREATE TABLE "
            + TABLE_EVENTS + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DAY + " TEXT NOT NULL, "
            + COLUMN_PRETTY_TIME + " TEXT, "
            + COLUMN_START_TIME + " TEXT NOT NULL, "
            + COLUMN_END_TIME + " TEXT NOT NULL, "
            + COLUMN_CATEGORIE + " TEXT, "
            + COLUMN_PRETTY_WEEK + " TEXT, "
            + COLUMN_WEEK_NUMBER + " INTEGER NOT NULL, "
            + COLUMN_MATIERE + " TEXT, "
            + COLUMN_GROUP + " TEXT, "
            + COLUMN_SALLE + " TEXT, "
            + COLUMN_NOTES + " TEXT"
            + ")";

    public static final String TABLE_FORMATIONS = "formations";
    public static final String COLUMN_FORMATION = "formation";
    public static final String COLUMN_URLFORMATION = "urlformation";
    public static final String COLUMN_GROUPE = "groupe";
    public static final String COLUMN_URLGROUPE = "urlgroupe";

    private static final String DATABASE_CREATE_FORMATION = "CREATE TABLE "
            + TABLE_FORMATIONS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FORMATION + " TEXT NOT NULL, "
            + COLUMN_URLFORMATION + " TEXT NOT NULL, "
            + COLUMN_GROUPE + " TEXT NOT NULL, "
            + COLUMN_URLGROUPE + " TEXT NOT NULL"
            + ")";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        removeDatabaseEvents(db);
        removeDatabaseFormations(db);

        createDatabaseEvents(db);
        createDatabaseFormations(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Database.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        removeDatabaseEvents(db);
        removeDatabaseFormations(db);

        onCreate(db);
    }

    public void removeDatabaseEvents(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
    }

    public void removeDatabaseFormations(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FORMATIONS);
    }

    public void createDatabaseEvents(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_EVENT);
    }

    public void createDatabaseFormations(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_FORMATION);
    }
}
