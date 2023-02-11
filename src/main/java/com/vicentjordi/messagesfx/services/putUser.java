package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class putUser extends Service<String> {
    String dataImage;

    public putUser(String dataImage) {
        this.dataImage = dataImage;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                Gson gson = new Gson();
                String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/users",
                        gson.toJson(dataImage), "PUT");
                return resp;
            }
        };
    }
}