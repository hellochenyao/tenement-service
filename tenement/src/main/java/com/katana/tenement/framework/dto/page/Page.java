package com.katana.tenement.framework.dto.page;

import lombok.Data;

import java.util.List;

/**
 * @author admin
 * @date 2018/4/12
 */
@Data
public class Page<T> {

    private int total;
    private int currentPage;
    private int pageSize;
    private List<T> data;
}
