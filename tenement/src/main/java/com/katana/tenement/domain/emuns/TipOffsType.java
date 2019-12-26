package com.katana.tenement.domain.emuns;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TipOffsType implements EnumsValue {

    TIPOFFS_INVITATION(0,"举报帖子"),
    TIPOFFS_SUGGEST(1,"意见反馈");

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
