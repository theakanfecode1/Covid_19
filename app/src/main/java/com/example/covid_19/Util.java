package com.example.covid_19;

public class Util {
    private String Country;
    private String confirmed;
    private String active;
    private String recovered;
    private String death;
    private String today;
    private String imageLinks;



    public Util(String country,String confirmed, String active, String recovered, String death, String twodaysAgo,String link) {
        this.Country = country;
        this.confirmed = confirmed;
        this.active = active;
        this.recovered = recovered;
        this.death = death;
        this.today = twodaysAgo;
        this.imageLinks = link;
    }
    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
    public String getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getTwodaysAgo() {
        return today;
    }

    public void setTwodaysAgo(String twodaysAgo) {
        this.today = twodaysAgo;
    }
}
