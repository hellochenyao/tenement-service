package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

@Data
public class RequestWordsToUserFilterGet {

    private int invitationId;

    private int pageNo=1;

    private int pageSize=10;

}
