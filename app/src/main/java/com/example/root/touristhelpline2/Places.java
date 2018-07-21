package com.example.root.touristhelpline2;

import android.graphics.Bitmap;

public class Places {
    int id;
    String imageUrl;
    String textUrl;
    int upvotes;
    String name;
    String location;

    public Places (int id, String imageUrl, String textUrl, int upvotes, String name, String location) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.textUrl = textUrl;
        this.upvotes = upvotes;
        this.name = name;
        this.location = location;
    }

    public int getId () {
        return this.id;
    }

    public String getImageUrl () {
        return this.imageUrl;
    }

    public String getTextUrl() {
        return this.textUrl;
    }

    public int getUpvotes() {
        return this.upvotes;
    }

    public String getName () {
        return this.name;
    }

    public String getLocation () {
        return this.location;
    }
}
