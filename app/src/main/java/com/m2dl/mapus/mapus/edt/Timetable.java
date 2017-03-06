package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.model.Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Timetable extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        try {
            Document doc = Jsoup.connect(params[0]).get();
            Elements events = doc.select("event");

            ArrayList<Event> eventsList = new ArrayList<>();
            for (Element event :
                    events) {
                eventsList.add(new Event(
                        Integer.valueOf(event.select("day").text()),
                        event.select("prettytimes").text(),
                        event.select("starttime").text(),
                        event.select("endtime").text(),
                        event.select("category").text(),
                        event.select("prettyweeks").text(),
                        event.select("rawweeks").text(),
                        event.select("module").text(),
                        event.select("group").text(),
                        event.select("room").text() ,
                        event.select("notes").text()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
