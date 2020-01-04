package com.katana.tenement.dao.app.vo.concern;

import com.katana.tenement.domain.emuns.ConcernType;
import lombok.Data;

@Data
public class ConcernFilterVo {
    private int userid;
    private int type;
    private ConcernType concernType;
    private int pageNo;
    private int pageSize;
}
