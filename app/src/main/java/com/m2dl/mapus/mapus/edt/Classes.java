package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Classes extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        try {
            Document doc = Jsoup.connect(params[0]).get();
            Elements formations = doc.select("event");

            Map<String, String> formationsMap = new HashMap<String, String>();

            for (Element formation:
                    formations) {
                if (!formation.attr("href").isEmpty()) {
                    formationsMap.put(formation.text(), formation.attr("href"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
