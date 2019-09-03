package com.katana.tenement.web.app.api.concern;

import lombok.Data;

@Data
public class RequestConcernUsersFilterGet {
    private int userid;
    private int pageNo=1;
    private int pageSize=10;
}
