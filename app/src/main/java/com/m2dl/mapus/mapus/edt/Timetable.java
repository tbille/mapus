package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;
import android.util.Log;

import com.m2dl.mapus.mapus.SettingsFragment;
import com.m2dl.mapus.mapus.database.EventDataSource;
import com.m2dl.mapus.mapus.model.Event;
import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Timetable extends AsyncTask<Object, Void, Long> {
    private SettingsFragment settingsFragment;

    @Override
    protected Long doInBackground(Object... params) {
        try {
            settingsFragment = (SettingsFragment) params[0];
            Formation formation = (Formation) params[1];
            EventDataSource db = (EventDataSource) params[2];

            db.open();

            Document doc = Jsoup.connect(formation.getUrlGroupe()).get();
            Elements events = doc.select("event");

            db.deleteDatabase();
            db.createDatabase();


            for (Element event :
                    events) {
                String rawweeks = event.select("rawweeks").text();
                int position = rawweeks.indexOf("Y") + 1;

                Elements spans = doc.select("span[rawix=" + String.valueOf(position) + "]");
                Element span = spans.get(0);
                String week = span.select("title").text();

                Log.d("DEBUG Timetable", "doInBackground: " + event.select("module").text());

                db.createEvent(new Event(
                    Integer.valueOf(event.select("day").text()),
                    event.select("prettytimes").text(),
                    event.select("starttime").text(),
                    event.select("endtime").text(),
                    event.select("category").text(),
                    event.select("prettyweeks").text(),
                    Integer.valueOf(week),
                    event.select("module").text(),
                    event.select("group").text(),
                    event.select("room").text() ,
                    event.select("notes").text()
                ));
            }
            Log.d("DEBUG Timetable", "doInBackground: Fini");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Long(1);
    }

    protected void onPostExecute(Long result) {
        settingsFragment.edtIsDownload();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
