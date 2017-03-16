package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.SettingsFragment;
import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Formations extends AsyncTask<Object, Void, Long> {

    private String urlEdt = "http://edt.univ-tlse3.fr/";
    private ArrayList<Formation> licences;
    private ArrayList<Formation> masters;

    private SettingsFragment settingsFragment;

    @Override
    protected Long doInBackground(Object... params) {

        settingsFragment = (SettingsFragment) params[0];

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
        settingsFragment.setLicences(licences);
        settingsFragment.setMasters(masters);
    }
}
