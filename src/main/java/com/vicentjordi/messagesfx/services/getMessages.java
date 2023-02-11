package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vicentjordi.messagesfx.models.Messages;
import com.vicentjordi.messagesfx.models.Users;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.lang.reflect.Type;
import java.util.List;

public class getMessages extends Service<List<Messages>> {
    @Override
    protected Task<List<Messages>> createTask() {
        return new Task<List<Messages>>() {
            @Override
            protected List<Messages> call() throws Exception {
                String json = ServiceUtils.getResponse(nodeServer.getServer() + "/messages",
                        null, "GET");
                Gson gson = new Gson();

                Type type = new TypeToken<List<Messages>>() {
                }.getType();
                List<Messages> messages = gson.fromJson(json, type);
                return messages;
            }
        };
    }
}