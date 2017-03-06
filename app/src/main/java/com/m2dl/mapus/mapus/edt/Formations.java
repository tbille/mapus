package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Formations extends AsyncTask<Void, Void, Void> {

    private String urlEdt = "http://edt.univ-tlse3.fr/";

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(urlEdt).get();
            Elements selects = doc.select("select");

            Elements licenceOptions = selects.get(0).select("option");
            Map<String, String> licenceMap = new HashMap<>();

            for (Element option:
                    licenceOptions) {
                if (!option.attr("value").isEmpty()) {
                    licenceMap.put(option.text(), option.attr("value"));
                }
            }

            Elements masterOptions = selects.get(1).select("option");
            Map<String, String> masterMap = new HashMap<>();

            for (Element option:
                    masterOptions) {
                if (!option.attr("value").isEmpty()) {
                    masterMap.put(option.text(), option.attr("value"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
