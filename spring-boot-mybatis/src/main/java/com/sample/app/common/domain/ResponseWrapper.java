package com.sample.app.common.domain;

import com.sample.app.common.constant.ReturnCode;

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
	 * 参数为空或者参数格式错误
	 * 
	 * @return
	 */
	public static ResponseWrapper markParamError() {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(false);
		responseWrapper.setCode(ReturnCode.PARAMS_ERROR.getCode());
		responseWrapper.setMsg(ReturnCode.PARAMS_ERROR.getMsg());
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

	/**
	 * 微信推送接口专用
	 *
	 * @param success
	 * @param code
	 * @return
	 */
	public static ResponseWrapper markWxPush(boolean success, String code,String msg) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(success);
		responseWrapper.setCode(code);
		responseWrapper.setMsg(msg);
		responseWrapper.setData(null);
		return responseWrapper;
	}


	/**
	 * 工作流验证专用
	 *
	 * @param success
	 * @param msg
	 * @param data
	 * @return
	 */
	public static ResponseWrapper customByVerifyWorkflow(boolean success, String msg, Object data) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(success);
		responseWrapper.setMsg(msg);
		responseWrapper.setData(data);
		return responseWrapper;
	}
}
