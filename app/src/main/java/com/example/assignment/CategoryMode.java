package com.example.assignment;

public class CategoryMode {
    private String imageURL,title;
    //this is the st
    public CategoryMode(String imageURL, String title)
    {
        this.imageURL = imageURL;
        this.title = title;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setImageUrl(String imageURL){
        this.imageURL = imageURL;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
