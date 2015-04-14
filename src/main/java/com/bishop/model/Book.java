package com.bishop.model;

public class Book extends Document {
    
    public Book(String title, String author, Topic topic) {
        this.content = Content.book;
        this.title = title;
        this.author = author;
        this.topic = topic;
    }
    
    private Topic topic;

    public Topic getTopic() {
        return topic;
    }

}
