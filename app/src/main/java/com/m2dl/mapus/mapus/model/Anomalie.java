package com.m2dl.mapus.mapus.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Anomalie {
    public String photo;
    public double longitude;
    public double lattitude;
    public String type;

    public Anomalie() {
    }

    public Anomalie(String photo, double longitude, double lattitude, String type) {
        this.photo = photo;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.type = type;
    }
}
