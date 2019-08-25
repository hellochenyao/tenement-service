package com.katana.tenement.domain.emuns;

/**
 * Created by mumu on 2019/4/19.
 */
public enum UserGenderEnum implements EnumsValue {

    //性别
    UNKNOWN(0, "未知"),
    MAN(1, "男"),
    WOMAN(2, "女");

    private int code;
    private String value;

    UserGenderEnum(int code, String value) {
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
