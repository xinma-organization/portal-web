package com.xinma.portal.log.error;

import com.xinma.base.core.error.CustomError;

public enum PortalCacheError implements CustomError {

	// 产品cache error定义
	GetOrgProductErr("portal-cache-product-001", "catch exception when get organization produts from redis."),

	GetProductFromRedisErr("portal-cache-product-002", "catch exception when get produt from redis."),

	SaveProductToRedis("portal-cache-product-003", "catch exception when save product object into redis.");

	String value;

	String description;

	PortalCacheError(String value, String description) {
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
