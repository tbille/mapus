package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Formations extends AsyncTask<Void, Void, Long> {

    private String urlEdt = "http://edt.univ-tlse3.fr/";
    private ArrayList<Formation> licences;
    private ArrayList<Formation> masters;

    @Override
    protected Long doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(urlEdt).get();
            Elements selects = doc.select("select");

            Elements licenceOptions = selects.get(0).select("option");
            licences = new ArrayList<>();

            for (Element option:
                    licenceOptions) {
                if (!option.attr("value").isEmpty()) {
                    licences.add(new Formation(option.attr("value").replace(".html", "2.html"), option.text()));
                }
            }

            Elements masterOptions = selects.get(1).select("option");
            masters = new ArrayList<>();

            for (Element option:
                    masterOptions) {
                if (!option.attr("value").isEmpty()) {
                    masters.add(new Formation(option.attr("value").replace(".html", "2.html"), option.text()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Long(1);
    }

    protected void onPostExecute(Long result) {

    }
}
