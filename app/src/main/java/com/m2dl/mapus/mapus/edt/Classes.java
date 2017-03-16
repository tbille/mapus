package com.m2dl.mapus.mapus.edt;

import android.os.AsyncTask;

import com.m2dl.mapus.mapus.model.Formation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Classes extends AsyncTask<Object, Void, Long> {
    Map<String, Formation> formationsMap;
    @Override
    protected Long doInBackground(Object... params) {
        try {
            Formation formationParam = (Formation) params[0];
            Document doc = Jsoup.connect(formationParam.getUrlFormation()).get();
            Elements formations = doc.select("a[class=ttlink]");

            formationsMap = new HashMap<String, Formation>();

            for (Element formation:
                    formations) {
                if (!formation.attr("href").isEmpty()) {
                    Formation tmp = new Formation(formation.attr("href").replace("html", "xml"), formationParam.getUrlFormation(), formationParam.getFormation(), formation.text());
                    formationsMap.put(formation.text(), tmp);
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
