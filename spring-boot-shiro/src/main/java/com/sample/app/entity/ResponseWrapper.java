package com.sample.app.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseWrapper {

	/** 是否成功 */
	private boolean success;
	/** 返回码 */
	private String code;
	/** 返回信息 */
	private String msg;
	/** 返回数据 */
	private Object data;

	/**
	 * 自定义返回结果 建议使用统一的返回结果，特殊情况可以使用此方法
	 * 
	 * @param success
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static ResponseWrapper markCustom(boolean success, String code, String msg, Object data) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(success);
		responseWrapper.setCode(code);
		responseWrapper.setMsg(msg);
		responseWrapper.setData(data);
		return responseWrapper;
	}


	/**
	 * 查询失败
	 * 
	 * @return
	 */
	public static ResponseWrapper markError() {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(false);
		responseWrapper.setCode(ReturnCode.FAILED.getCode());
		responseWrapper.setMsg(ReturnCode.FAILED.getMsg());
		responseWrapper.setData(null);
		return responseWrapper;
	}

	/**
	 * 查询成功且有数据
	 * 
	 * @param data
	 * @return
	 */
	public static ResponseWrapper markSuccess(Object data) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(true);
		responseWrapper.setCode(ReturnCode.SUCCESS.getCode());
		responseWrapper.setMsg(ReturnCode.SUCCESS.getMsg());
		responseWrapper.setData(data);
		return responseWrapper;
	}
}
