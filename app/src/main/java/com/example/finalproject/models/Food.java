package com.example.finalproject.models;

public class Food {
    private long id;
    private int sweet;
    private int salty;
    private int sour;
    private int bitter;
    private int umami;
    private String countryOfOrigin;
    private boolean spicy;
    private String name;
    private String description;

    public Food(long id, int sweet, int salty, int sour, int bitter, int umami, String countryOfOrigin, boolean spicy, String name, String description) {
        this.id = id;
        this.sweet = sweet;
        this.salty = salty;
        this.sour = sour;
        this.bitter = bitter;
        this.umami = umami;
        this.countryOfOrigin = countryOfOrigin;
        this.spicy = spicy;
        this.name = name;
        this.description = description;
    }

    public Food(int sweet, int salty, int sour, int bitter, int umami, String countryOfOrigin, boolean spicy, String name, String description) {
        this.sweet = sweet;
        this.salty = salty;
        this.sour = sour;
        this.bitter = bitter;
        this.umami = umami;
        this.countryOfOrigin = countryOfOrigin;
        this.spicy = spicy;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSweet() {
        return sweet;
    }

    public void setSweet(int sweet) {
        this.sweet = sweet;
    }

    public int getSalty() {
        return salty;
    }

    public void setSalty(int salty) {
        this.salty = salty;
    }

    public int getSour() {
        return sour;
    }

    public void setSour(int sour) {
        this.sour = sour;
    }

    public int getBitter() {
        return bitter;
    }

    public void setBitter(int bitter) {
        this.bitter = bitter;
    }

    public int getUmami() {
        return umami;
    }

    public void setUmami(int umami) {
        this.umami = umami;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public boolean isSpicy() {
        return spicy;
    }

    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", sweet=" + sweet +
                ", salty=" + salty +
                ", sour=" + sour +
                ", bitter=" + bitter +
                ", umami=" + umami +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", spicy=" + spicy +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
