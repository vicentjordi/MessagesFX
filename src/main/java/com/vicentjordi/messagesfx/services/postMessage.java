package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Messages;
import com.vicentjordi.messagesfx.models.Users;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class postMessage extends Service<String> {
    Messages messages;

    public postMessage(Messages messages) {
        this.messages = messages;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                Gson gson = new Gson();
                String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/messages/" + messages.getTo(),
                        gson.toJson(messages), "POST");
                return resp;
            }
        };
    }
}
