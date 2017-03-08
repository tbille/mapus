package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.database.EventDataSource;
import com.m2dl.mapus.mapus.model.Event;
import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Timetable extends AsyncTask<Object, Void, Void> {
    @Override
    protected Void doInBackground(Object... params) {
        try {
            Formation formation = (Formation) params[0];
            EventDataSource db = (EventDataSource) params[1];

            Document doc = Jsoup.connect(formation.getUrl()).get();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
