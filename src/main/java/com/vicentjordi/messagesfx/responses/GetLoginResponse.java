package com.vicentjordi.messagesfx.responses;

public class GetLoginResponse extends OkResponse{
    private String token;
    private String name;
    private String image;

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
