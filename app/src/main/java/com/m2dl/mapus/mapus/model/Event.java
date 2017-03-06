package com.m2dl.mapus.mapus.model;

public class Event {

    private int day;
    private String prettyTime;
    private String startTime;
    private String endTime;
    private String categorie;
    private String prettyWeek;

    private int weekNumber;

    private String matiere;
    private String group;
    private String salle;
    private String notes;

    public Event(int day, String prettyTime, String startTime, String endTime, String categorie, String prettyWeek, int weekNumber, String matiere, String group, String salle, String notes) {
        this.day = day;
        this.prettyTime = prettyTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categorie = categorie;
        this.prettyWeek = prettyWeek;
        this.weekNumber = weekNumber;
        this.matiere = matiere;
        this.group = group;
        this.salle = salle;
        this.notes = notes;
    }

    public Event(int day, String prettyTime, String startTime, String endTime, String categorie, String prettyWeek, String rawWeek, String matiere, String group, String salle, String notes) {
        this.day = day;
        this.prettyTime = prettyTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categorie = categorie;
        this.prettyWeek = prettyWeek;
        this.matiere = matiere;
        this.group = group;
        this.salle = salle;
        this.notes = notes;

        this.weekNumber = rawWeek.indexOf("Y");
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getPrettyTime() {
        return prettyTime;
    }

    public void setPrettyTime(String prettyTime) {
        this.prettyTime = prettyTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getPrettyWeek() {
        return prettyWeek;
    }

    public void setPrettyWeek(String prettyWeek) {
        this.prettyWeek = prettyWeek;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }
}
