package com.katana.tenement.domain.emuns;

public enum  RentTypeEnum implements EnumsValue{

    ZHENG_ZU(0,"整租"),
    ZHU_WO(1,"主卧"),
    CI_WO(2,"次卧"),
    CHUANG(3,"床位"),;

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
