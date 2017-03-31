package com.xinma.portal.cache.product;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.util.JacksonUtils;
import com.xinma.message.service.ProductTO;
import com.xinma.portal.common.RedisConstants;
import com.xinma.portal.log.PortalCustomException;
import com.xinma.portal.log.error.PortalCacheError;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 产品缓存类
 * 
 * @author Alauda
 *
 */
@Component("productCache")
public class ProductCacheImpl implements ProductCache {

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 每个企业产品Id列表
	 */
	private Map<Integer, Set<Integer>> eseProductIdsMap = new ConcurrentHashMap<Integer, Set<Integer>>();

	/**
	 * 产品Id与产品对象的映射
	 */
	private Map<Integer, ProductTO> productsMap = new ConcurrentHashMap<Integer, ProductTO>();

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Set<Integer> getProductIdsSetByOrgId(int orgId) {

		Set<Integer> productIds = eseProductIdsMap.get(orgId);
		if (CollectionUtils.isEmpty(productIds)) {

			productIds = getProductIdsByOrgIdFromRedis(orgId);
			if (CollectionUtils.isNotEmpty(productIds)) {
				eseProductIdsMap.put(orgId, productIds);
			}

		}
		return productIds;
	}

	@Override
	public Set<ProductTO> getProductSetByOrgId(int orgId) {
		Set<Integer> productIds = getProductIdsSetByOrgId(orgId);
		if (CollectionUtils.isNotEmpty(productIds)) {
			Set<ProductTO> sets = new HashSet<>();
			for (Integer id : productIds) {
				ProductTO product = getProductById(id);
				if (product != null) {
					sets.add(product);
				}
			}

			return sets;
		} else {
			return null;
		}
	}

	@Override
	public void saveProductToRedis(ProductTO product) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			// 保存产品对象到redis
			String productKey = getProductKeyByProductId(product.getOid());
			jedis.set(productKey, mapper.writeValueAsString(product));

			// 保存产品Id到企业数组
			Set<Integer> productIds = getProductIdsByOrgIdFromRedis(product.getEseId());
			productIds.add(product.getOid());
			jedis.set(getOrgProductsKeyByorgId(product.getEseId()), mapper.writeValueAsString(productIds));
		} catch (Exception e) {
			throw new PortalCustomException(e, PortalCacheError.SaveProductToRedis);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}

	}

	@Override
	public ProductTO getProductById(int productId) {
		ProductTO product = productsMap.get(productId);
		if (product == null) {

			product = getProductByIdFromRedis(productId);
			if (product != null) {
				productsMap.put(productId, product);
			}
		}
		return product;
	}

	private ProductTO getProductByIdFromRedis(int productId) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			String key = getProductKeyByProductId(productId);
			String value = jedis.get(key);
			if (StringUtils.isNotBlank(value)) {
				return mapper.readValue(value, ProductTO.class);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new PortalCustomException(e, PortalCacheError.GetProductFromRedisErr);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	private Set<Integer> getProductIdsByOrgIdFromRedis(int orgId) {

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			String key = getOrgProductsKeyByorgId(orgId);
			String value = jedis.get(key);
			if (StringUtils.isBlank(value)) {
				return new HashSet<>();
			} else {
				return JacksonUtils.readJson2EntitySet(value, Integer.class);
			}
		} catch (Exception e) {
			throw new PortalCustomException(e, PortalCacheError.GetOrgProductErr);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 根据产品Id获取产品缓存对象的key
	 * 
	 * @param productId
	 *            产品Id
	 * @return 产品缓存对象的key
	 */
	private String getProductKeyByProductId(int productId) {
		return RedisConstants.productPrefix + productId;
	}

	/**
	 * 根据组织Id获取组织下的所有产品Id缓存的key
	 * 
	 * @param orgId
	 *            组织ID
	 * @return 组织下产品缓存对象的key
	 */
	private String getOrgProductsKeyByorgId(int orgId) {
		return RedisConstants.orgProductsPrefix + orgId;
	}

}
