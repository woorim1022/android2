package com.example.tabapplication.models;

public class Bookmark {

    private String title;
    private String address;
    private String filename;

    public Bookmark(String address, String filename){
        this.title = address.length() > 12 ? address.substring(0, 12) + "..." : address;
        this.address = address;
        this.filename = filename;
    }

    public String getTitle() {
        return title;
    }

    public String getAddres() {
        return address;
    }

    public String getFile(){
        return filename;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Bookmark && address.equals(((Bookmark) object).getAddres());
    }


}