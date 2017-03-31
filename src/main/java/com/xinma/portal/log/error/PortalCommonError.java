package com.xinma.portal.log.error;

import com.xinma.base.core.error.CustomError;

public enum PortalCommonError implements CustomError {

	CatchThrowableException("portal-common-001", "catch ThrowableException in PortalExceptionHandler.");

	String value;

	String description;

	PortalCommonError(String value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public String value() {
		return value;
	}

	@Override
	public String description() {
		return description;
	}
}
