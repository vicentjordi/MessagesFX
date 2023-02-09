package com.vicentjordi.messagesfx.responses;

import com.vicentjordi.messagesfx.models.Users;

public class GetUsersResponse extends OkResponse{
    private Users user;

    public Users getUser() {
        return user;
    }
}
