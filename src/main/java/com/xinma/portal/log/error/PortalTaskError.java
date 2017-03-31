package com.xinma.portal.log.error;

import com.xinma.base.core.error.CustomError;

public enum PortalTaskError implements CustomError {

	PortalDownloadMessageTask("portal-task-001", "catch exception in PortalDownloadMessageTask.");

	String value;

	String description;

	PortalTaskError(String value, String description) {
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
