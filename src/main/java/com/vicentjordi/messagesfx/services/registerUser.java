package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Users;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class registerUser extends Service<String> {
    Users user;

    public registerUser(Users user) {
        this.user = user;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                Gson gson = new Gson();
                String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/register",
                    gson.toJson(user), "POST");
                return resp;
            }
        };
    }



}
