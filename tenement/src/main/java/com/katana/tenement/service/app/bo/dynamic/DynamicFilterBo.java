package com.katana.tenement.service.app.bo.dynamic;

import lombok.Data;

@Data
public class DynamicFilterBo {
    private int type=0;
    private Integer userId;
    private int dynamicId;
    private int pageNo;
    private int pageSize;
}
