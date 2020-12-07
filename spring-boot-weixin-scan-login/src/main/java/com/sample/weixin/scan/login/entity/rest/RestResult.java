package com.sample.weixin.scan.login.entity.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 */
@Data
public class RestResult<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer CODE_OK = 0;
    public static final Integer CODE_FAIL = 1;

    private Integer code;
    private String msg;
    private T data;
    private String result;

    private RestResult() {
    }

    public static <T> RestResult<T> newInstance() {
        return new RestResult<T>();
    }
}
