package com.katana.tenement.domain.emuns;

public enum  InvitationTypeEnum implements EnumsValue {

    NO_HOUSING_PUBLISH(0,"求租帖"),
    HOUSING_PUBLISH(1,"招租帖"),
    TURN_HOUSING_PUBLISH(2,"转租帖");

    private int code;
    private String value;

    InvitationTypeEnum(int code,String value){
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
