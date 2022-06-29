package com.naiimab.firstguide.Models;

public class ModelItems {

    private int position;
    private String title;
    private String image;
    private String color;
    private String text_size;
    private boolean isLink;
    private String link_title;
    private String setLink;
    private String image_link;
    private String text;
    private boolean isNative;
    private String background;


    public ModelItems(int position, String title, String image) {
        this.position = position;
        this.title = title;
        this.image = image;
    }

    public ModelItems(int position, String title, String image, String color, String text_size, boolean isLink, String link_title, String setLink, String image_link, String text, boolean isNative, String background) {
        this.position = position;
        this.title = title;
        this.image = image;
        this.color = color;
        this.text_size = text_size;
        this.isLink = isLink;
        this.link_title = link_title;
        this.setLink = setLink;
        this.image_link = image_link;
        this.text = text;
        this.isNative = isNative;
        this.background = background;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getColor() {
        return color;
    }

    public String getText_size() {
        return text_size;
    }

    public boolean getIsLink() {
        return isLink;
    }

    public String getLink_title() {
        return link_title;
    }

    public String getSetLink() {
        return setLink;
    }

    public String getImage_link() {
        return image_link;
    }

    public String getText() {
        return text;
    }

    public boolean getIsNative() {
        return isNative;
    }

    public String getBackground() {
        return background;
    }
}
