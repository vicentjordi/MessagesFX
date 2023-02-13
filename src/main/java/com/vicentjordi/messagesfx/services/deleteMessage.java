package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Messages;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class deleteMessage extends Service<String> {
    String idMessage;

    public deleteMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                Gson gson = new Gson();
                String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/messages/" + idMessage,
                        null, "DELETE");
                return resp;
            }
        };
    }
}