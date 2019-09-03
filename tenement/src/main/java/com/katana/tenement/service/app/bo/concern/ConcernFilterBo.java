package com.katana.tenement.service.app.bo.concern;

import lombok.Data;

@Data
public class ConcernFilterBo {
    private int userid;
    private int type;
    private int pageNo;
    private int pageSize;
}
