package com.katana.tenement.domain.emuns;

public enum  RentTypeEnum implements EnumsValue{

    ZHENG_ZU(0,"整租"),
    DUAN_ZU(1,"短租"),
    HE_ZU(2,"合租");

    private int code;

    private String value;

    RentTypeEnum(int code,String value){
        this.code = code;
        this.value = value;
    }
    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getValue() {
        return null;
    }
}
