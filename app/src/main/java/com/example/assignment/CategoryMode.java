package com.example.assignment;

public class CategoryMode {
    private String imageURL, title;
    private int sets;
    private String url;
    private String name;

    public CategoryMode(){
        // for firebase
    }

    public CategoryMode(int sets, String url, String name) {
        this.sets = sets;
        this.url = url;
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


