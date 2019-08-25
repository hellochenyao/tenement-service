package com.katana.tenement.framework.page;

import lombok.Data;

import java.util.List;

/**
 * Created by mumu on 2019/4/17.
 */
@Data
public class PagerResult<T> {

    /**
     * 总数
     */
    private long total;
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 页数
     */
    private int pageSize;
    /**
     * 分页数据
     */
    private List<T> data;
}
