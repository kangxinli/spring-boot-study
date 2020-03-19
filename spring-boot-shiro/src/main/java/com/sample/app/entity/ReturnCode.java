package com.sample.app.entity;

public enum ReturnCode {
	
	UNAUTHORIZED("401", "没有权限"),
	SUCCESS("0000","操作成功"),
    FAILED("0001","操作失败"),
    LOGIN_FAIL("0002", "登录失败"),
    API_ERROR("1000", "请求的接口异常"),
    PARAMS_ERROR("1001", "参数为空或格式错误"),
    SYSTEM_ERROR("9999", "系统异常");
 
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
