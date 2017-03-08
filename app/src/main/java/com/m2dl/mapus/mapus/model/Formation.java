package com.m2dl.mapus.mapus.model;

public class Formation {
    private String url;
    private String formation;
    private String groupe;

    public Formation() {

    }

    public Formation(String formation) {
        this.url = url;
        this.formation = formation;
        this.groupe = groupe;
    }

    public Formation(String formation, String groupe) {
        this.url = url;
        this.formation = formation;
        this.groupe = groupe;
    }

    public Formation(String formation, String groupe, String url) {
        this.url = url;
        this.formation = formation;
        this.groupe = groupe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
