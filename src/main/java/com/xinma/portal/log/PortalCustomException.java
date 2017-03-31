package com.xinma.portal.log;

import com.xinma.base.core.error.CustomError;
import com.xinma.base.core.exceptions.CustomException;

/**
 * 系统自定义异常，继承自RuntimeException
 * 
 * @author Alauda
 *
 * @date 2016年5月19日
 *
 */
public class PortalCustomException extends CustomException {

	private static final long serialVersionUID = -4713824450047222637L;

	public PortalCustomException() {
		super();
	}

	public PortalCustomException(String message) {
		super(message);
	}

	public PortalCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public PortalCustomException(Throwable cause) {
		super(cause);
	}

	/**
	 * 构造函数
	 * 
	 * @param message
	 *            异常描述信息
	 * @param error
	 *            错误代码
	 * @param params
	 *            错误信息需要的参数
	 */
	public PortalCustomException(String message, CustomError error, String... params) {

		super(message, error, params);
	}

	/**
	 * 构造函数
	 * 
	 * @param error
	 *            CustomError对象
	 * @param params
	 *            错误信息需要的参数列表
	 */
	public PortalCustomException(CustomError error, String... params) {
		super(error, params);
	}

	/**
	 * 构造函数
	 * 
	 * @param cause
	 *            Throwable Object
	 * @param error
	 *            CustomError对象
	 * @param params
	 *            错误信息需要的参数列表
	 */
	public PortalCustomException(Throwable cause, CustomError error, String... params) {
		super(cause, error, params);
	}
}
