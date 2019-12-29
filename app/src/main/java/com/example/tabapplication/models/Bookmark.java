package com.example.tabapplication.models;

public class Bookmark {

    private String title;
    private String address;

    public Bookmark(String address){
        this.title = address.length() > 12 ? address.substring(0, 12) + "..." : address;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddres() {
        return address;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Bookmark && address.equals(((Bookmark) object).getAddres());
    }


}
