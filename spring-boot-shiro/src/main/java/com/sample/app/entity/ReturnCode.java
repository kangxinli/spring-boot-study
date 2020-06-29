package com.sample.app.entity;

public enum ReturnCode {
	
	SUCCESS("200","操作成功"),
	UNAUTHORIZED("401", "身份验证"),
	FORBIDDEN("403", "没有访问权限"),
    FAILED("500","操作失败"),
    SYSTEM_ERROR("503", "系统维护");
 
    private String code;
    private String msg;
 
    public String getCode() {
        return code;
    }
 
    public String getMsg() {
        return msg;
    }
 
    ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
