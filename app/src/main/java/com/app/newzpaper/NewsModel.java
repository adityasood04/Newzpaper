package com.app.newzpaper;

public class NewsModel {
    String heading;
    String description;
    String date;
    String time;
    String imageUrl;
    String detailedUrl;

    public NewsModel(String heading, String description, String date, String time, String imageUrl, String detailedUrl) {
        this.heading = heading;
        this.description = description;
        this.date = date;
        this.time = time;
        this.imageUrl = imageUrl;
        this.detailedUrl = detailedUrl;
    }

    public String getDetailedUrl() {
        return detailedUrl;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
