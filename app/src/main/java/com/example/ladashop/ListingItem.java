package com.example.ladashop;

public class ListingItem {
    private String nev;
    private String leiras;
    private String ar;
    private final int imgResource;

    public ListingItem(String nev, String leiras, String ar, int imgResource) {
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.imgResource = imgResource;
    }

    public String getNev() {
        return nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public String getAr() {
        return ar;
    }

    public int getImgResource() {
        return imgResource;
    }
}
