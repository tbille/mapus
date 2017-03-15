package com.m2dl.mapus.mapus.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Batiment {

    public String nom;
    public Double longitude;
    public Double lattitude;
    public String snippet;

    public Batiment() {
    }

    public Batiment(String nom, Double lattitude, Double longitude, String snippet) {
        this.nom = nom;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.snippet = snippet;
    }
}
