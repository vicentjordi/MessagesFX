package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vicentjordi.messagesfx.models.Users;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.lang.reflect.Type;
import java.util.List;

public class getUsers extends Service<List<Users>> {
    @Override
    protected Task<List<Users>> createTask() {
        return new Task<List<Users>>() {
            @Override
            protected List<Users> call() throws Exception {
                String json = ServiceUtils.getResponse(nodeServer.getServer() + "/users",
                        null, "GET");
                Gson gson = new Gson();

                Type type = new TypeToken<List<Users>>() {
                }.getType();
                List<Users> user = gson.fromJson(json, type);
                return user;
            }
        };
    }
}
