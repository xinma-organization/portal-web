package com.xinma.portal.log.error;

import com.xinma.base.core.error.CustomError;

public enum IpLocationError implements CustomError {

	JuheIpLocationException("portal-iplocation-001", "catch exception when call juhe ip location api.");

	String value;

	String description;

	IpLocationError(String value, String description) {
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
