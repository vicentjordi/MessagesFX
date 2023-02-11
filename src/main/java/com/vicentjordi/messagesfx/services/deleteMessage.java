package com.vicentjordi.messagesfx.services;

import com.google.gson.Gson;
import com.vicentjordi.messagesfx.models.Messages;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class deleteMessage extends Service<Boolean> {
    String idMessage;

    public deleteMessage(String idMessage) {
        this.idMessage = idMessage;
    }

    @Override
    protected Task<Boolean> createTask() {
        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                Gson gson = new Gson();
                String resp = ServiceUtils.getResponse(nodeServer.getServer() + "/messages/" + idMessage,
                        null, "DELETE");
                return Boolean.parseBoolean(resp);
            }
        };
    }
}