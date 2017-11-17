package com.william.logging.api;


public class LoggerDefine {

    private String module;

    private String describe;

    private String type;

    public LoggerDefine(String module, String describe, String type) {
        this.module = module;
        this.describe = describe;
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

