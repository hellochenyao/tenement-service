package com.katana.tenement.service.app.bo.concern;

import com.katana.tenement.domain.emuns.ConcernType;
import lombok.Data;

@Data
public class ConcernFilterBo {
    private Integer userid;
    private int type;
    private ConcernType concernType;
    private int pageNo;
    private int pageSize;
}
