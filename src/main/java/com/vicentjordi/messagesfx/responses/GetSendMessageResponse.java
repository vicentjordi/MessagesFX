package com.vicentjordi.messagesfx.responses;

import com.vicentjordi.messagesfx.models.Messages;

public class GetSendMessageResponse extends OkResponse{
    Messages message;

    public Messages getMessage() {
        return message;
    }
}
