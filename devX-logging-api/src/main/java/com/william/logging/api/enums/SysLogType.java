package com.william.logging.api.enums;

/**
 * Created by sungang on 2017/11/2.
 */
public enum SysLogType {

    //分类 Id 1-登录 2-访问 3-操作 4-异常
    LOGIN("1"), AECCESS("2"), OPER("3"), EXCEPTION("4");


    public String value;

    SysLogType(String value){
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
