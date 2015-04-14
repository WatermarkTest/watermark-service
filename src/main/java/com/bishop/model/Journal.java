package com.bishop.model;

public class Journal extends Document {

    public Journal(String title, String author) {
        this.content = Content.journal;
        this.title = title;
        this.author = author;
    }

}
