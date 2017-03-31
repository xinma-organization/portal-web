package com.xinma.portal.common;

public class RedisConstants {
	/**
	 * 产品缓存在redis中key的前缀，key的后缀名为产品ID,例如product:1
	 */
	public final static String productPrefix = "product:";

	/**
	 * 组织下所有产品缓存在redis中的前缀，key的后缀名为组织ID,例如org:products:1
	 */
	public final static String orgProductsPrefix = "org:products:";
}
