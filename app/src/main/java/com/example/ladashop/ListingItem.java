package com.example.ladashop;

public class ListingItem {
    private String id;
    private String nev;
    private String leiras;
    private String ar;
    private  int imgResource;
    private int toCartCount;
    private int cartedCount;

    public ListingItem() {

    }

    public ListingItem(String nev, String leiras, String ar, int imgResource, int cartedCount) {
        this.nev = nev;
        this.leiras = leiras;
        this.ar = ar;
        this.imgResource = imgResource;
        this.toCartCount = toCartCount;
        this.cartedCount = cartedCount;
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

    public int getToCartCount() {
        return toCartCount;
    }

    public String _getID() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public int getCartedCount() {
        return cartedCount;
    }
}
