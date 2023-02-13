package com.vicentjordi.messagesfx.models;


import com.google.gson.annotations.SerializedName;
import javafx.scene.image.ImageView;

public class Users {
    @SerializedName("_id")
    private String id;
    private String name;
    private String password;
    private String image;

    public Users(String name, String password, String image) {
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public Users(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Users(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public ImageView getImageView() {
        ImageView imgView = new ImageView(getImage());
        imgView.setFitHeight(30);
        imgView.setPreserveRatio(true);

        return imgView;
    }

    @Override
    public String toString(){
        return "_id: " + id + ", name: " + name + ", password: " + password +
                ", image: " + image;
    }

}
