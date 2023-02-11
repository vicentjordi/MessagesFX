package com.vicentjordi.messagesfx.models;

import com.google.gson.annotations.SerializedName;

public class Messages {
    @SerializedName("_id")
    private String id;
    private String from;
    private String to;
    private String message;
    private String image;
    private String sent;

    public Messages(String message, String image, String sent) {
        this.message = message;
        this.image = image;
        this.sent = sent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    @Override
    public String toString(){
        return "from: " + from + ", to: " + to + ", message: " + message +
                ", image: " + image +", sent: " + sent;
    }
}
