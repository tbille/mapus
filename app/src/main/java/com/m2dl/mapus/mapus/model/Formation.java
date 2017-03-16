package com.m2dl.mapus.mapus.model;

public class Formation {
    private String urlGroupe;
    private String urlFormation;
    private String formation;
    private String groupe;

    public Formation() {

    }

    public Formation(String urlFormation, String formation) {
        this.urlFormation = urlFormation;
        this.formation = formation;
    }

    public Formation(String urlGroupe, String urlFormation, String formation, String groupe) {
        this.urlGroupe = urlGroupe;
        this.urlFormation = urlFormation;
        this.formation = formation;
        this.groupe = groupe;
    }

    public String getUrlGroupe() {
        return urlGroupe;
    }

    public void setUrlGroupe(String urlGroupe) {
        this.urlGroupe = urlGroupe;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }

    public String getUrlFormation() {
        return urlFormation;
    }

    public void setUrlFormation(String urlFormation) {
        this.urlFormation = urlFormation;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "urlGroupe='" + urlGroupe + '\'' +
                ", urlFormation='" + urlFormation + '\'' +
                ", formation='" + formation + '\'' +
                ", groupe='" + groupe + '\'' +
                '}';
    }
}
