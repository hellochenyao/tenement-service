package com.katana.tenement.web.app.api.userRelation;

import lombok.Data;

@Data
public class RequestUserRelationPost {
    private int userid;
    private int friendId;
    private int type;
}
