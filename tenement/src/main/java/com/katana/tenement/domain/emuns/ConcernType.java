package com.katana.tenement.domain.emuns;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum  ConcernType implements EnumsValue{

    USER(0,"用户"),
    CIRCLE(1,"圈子");

    private int code;

    private String value;

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getValue() {
        return null;
    }
}
