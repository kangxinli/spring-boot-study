package com.sample.app.domain;

import com.sample.app.constant.ReturnCode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {

	/** 是否成功 */
	private boolean success;
	/** 返回码 */
	private String code;
	/** 返回信息 */
	private String msg;
	/** 返回数据 */
	private T data;

	/**
	 * 自定义返回结果 建议使用统一的返回结果，特殊情况可以使用此方法
	 * 
	 * @param success
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public static <T> BaseResponse<T> markCustom(boolean success, String code, String msg, T data) {
		BaseResponse<T> responseWrapper = new BaseResponse<T>();
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
	public static <T> BaseResponse<T> markError() {
		BaseResponse<T> responseWrapper = new BaseResponse<T>();
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
	public static <T> BaseResponse<T> markSuccess(T data) {
		BaseResponse<T> responseWrapper = new BaseResponse<T>();
		responseWrapper.setSuccess(true);
		responseWrapper.setCode(ReturnCode.SUCCESS.getCode());
		responseWrapper.setMsg(ReturnCode.SUCCESS.getMsg());
		responseWrapper.setData(data);
		return responseWrapper;
	}
}
