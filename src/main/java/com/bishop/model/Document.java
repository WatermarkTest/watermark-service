package com.bishop.model;

/**
 * Represents any type of document: books or journals
 *
 */
public abstract class Document {
    
    protected Content content;
    
    protected String title;
    
    protected String author;
    
    private transient String watermark;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

}
