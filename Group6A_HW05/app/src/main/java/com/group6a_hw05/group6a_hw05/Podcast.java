package com.group6a_hw05.group6a_hw05;

import java.io.Serializable;

/**
 * Created by Arunkumar's on 10/14/2015.
 */
public class Podcast implements Serializable{
    String title, description, image,
            duration, publicationDate, audio;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", duration='" + duration + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", audio='" + audio + '\'' +
                '}';
    }
}
