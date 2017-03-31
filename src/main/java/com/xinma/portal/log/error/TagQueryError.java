package com.xinma.portal.log.error;

import com.xinma.base.core.error.CustomError;

public enum TagQueryError implements CustomError {

	TagDecodeErr("portal-query-001", "tag decode error."),

	TagNotInCloudErr("portal-query-002", "the tag is not in cloud."),

	TagDifferFromCloudErr("portal-query-003", "the decode tag is differ with tag in cloud."),

	;

	String value;

	String description;

	TagQueryError(String value, String description) {
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
