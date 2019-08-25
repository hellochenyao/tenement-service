package com.katana.tenement.web.app.api.userRelation;

import lombok.Data;

@Data
public class RequestUserRelationDelete {
    private int userid;
    private int friendId;
}
