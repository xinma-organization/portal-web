package com.xinma.portal.cache.product;

import java.util.Set;

import com.xinma.message.service.ProductTO;

/**
 * 产品Redis缓存和内存缓存
 * 
 * @author Alauda
 *
 */
public interface ProductCache {

	/**
	 * 根据组织Id获取组织下的所有产品Id的集合
	 * 
	 * @param orgId
	 *            组织Id
	 * @return 企业产品Id集合
	 */
	public Set<Integer> getProductIdsSetByOrgId(int orgId);

	/**
	 * 根据组织Id获取组织下的所有产品对象的集合
	 * 
	 * @param orgId
	 *            组织Id
	 * @return 企业产品对象集合
	 */
	public Set<ProductTO> getProductSetByOrgId(int orgId);

	/**
	 * 根据产品Id获取产品对象
	 * 
	 * @param productId
	 *            产品Id
	 * @return ProductTO
	 */
	public ProductTO getProductById(int productId);

	/**
	 * 保存产品对象到redis
	 * 
	 * @param product
	 *            产品对象
	 */
	public void saveProductToRedis(ProductTO product);
}
