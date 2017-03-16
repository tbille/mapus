package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.SettingsFragment;
import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Classes extends AsyncTask<Object, Void, Long> {
    private ArrayList<Formation> formationsArray;

    private SettingsFragment settingsFragment;

    @Override
    protected Long doInBackground(Object... params) {

        try {
            settingsFragment = (SettingsFragment) params[0];
            Formation formationParam = (Formation) params[1];
            Document doc = Jsoup.connect(formationParam.getUrlFormation()).get();
            Elements formations = doc.select("a[class=ttlink]");

            formationsArray = new ArrayList<>();
            String url = formationParam.getUrlFormation().replace("finder2.html", "");

            for (Element formation:
                    formations) {
                if (!formation.attr("href").isEmpty()) {
                    Formation tmp = new Formation(url + formation.attr("href").replace("html", "xml"), formationParam.getUrlFormation(), formationParam.getFormation(), formation.text());
                    formationsArray.add(tmp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Long(1);
    }

    protected void onPostExecute(Long result) {
        settingsFragment.setClasses(formationsArray);
    }
}
