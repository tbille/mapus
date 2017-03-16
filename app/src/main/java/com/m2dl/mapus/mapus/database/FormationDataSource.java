package com.m2dl.mapus.mapus.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.m2dl.mapus.mapus.model.Formation;

import java.util.ArrayList;
import java.util.List;

public class FormationDataSource {

    private SQLiteDatabase database;
    private Database db;
    private String[] allColumns = {
            db.COLUMN_ID,
            db.COLUMN_FORMATION,
            db.COLUMN_URLFORMATION,
            db.COLUMN_GROUPE,
            db.COLUMN_URLGROUPE
    };

    public FormationDataSource(Context context) {
        db = new Database(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void createFormation(Formation formation) {
        ContentValues values = new ContentValues();

        values.put(Database.COLUMN_FORMATION, formation.getFormation());
        values.put(Database.COLUMN_URLFORMATION, formation.getFormation());
        values.put(Database.COLUMN_GROUPE, formation.getGroupe());
        values.put(Database.COLUMN_URLGROUPE, formation.getUrlGroupe());

        database.insert(
                Database.TABLE_FORMATIONS,
                null,
                values
        );
    }

    public List<Formation> getAllFormations() {
        List<Formation> formations = new ArrayList<>();

        Cursor cursor = database.query(
                Database.TABLE_FORMATIONS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Formation formation = cursorToFormation(cursor);
            formations.add(formation);
            cursor.moveToNext();
        }

        cursor.close();
        return formations;
    }

    private Formation cursorToFormation(Cursor cursor) {
        Formation formation = new Formation();
        formation.setFormation(cursor.getString(1));
        formation.setUrlFormation(cursor.getString(2));
        formation.setGroupe(cursor.getString(3));
        formation.setUrlGroupe(cursor.getString(4));

        return formation;
    }

    public void createDatabase() {
        db.createDatabaseFormations(database);
    }

    public void deleteDatabase() {
        db.removeDatabaseFormations(database);
    }
}
