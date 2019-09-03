package com.katana.tenement.dao.app.vo.concern;

import lombok.Data;

@Data
public class ConcernFilterVo {
    private int userid;
    private int type;
    private int pageNo;
    private int pageSize;
}
