package com.katana.tenement.domain.emuns;

public enum  UserLikeEnum implements EnumsValue{

    LIKE(0,"顶"),
    UNLIKE(1,"踩"),
    PLAIN(-1,"普通");

    private int code;
    private String value;

    UserLikeEnum(int code,String value){
        this.code = code;
        this.value = value;
    }
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
