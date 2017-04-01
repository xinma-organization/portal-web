package com.xinma.portal.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinma.base.util.ServletRequestHelper;
import com.xinma.log.model.TagQueryLogTO;
import com.xinma.message.service.ProductTO;
import com.xinma.portal.cache.product.ProductCache;
import com.xinma.portal.log.PortalCustomException;
import com.xinma.portal.log.error.TagQueryError;
import com.xinma.portal.model.TagQueryResult;
import com.xinma.tag.model.TagAccessLogEO;
import com.xinma.tag.model.TagBasicInfoEO;
import com.xinma.tag.model.location.AddressEO;
import com.xinma.tag.service.TagTableService;

@Service
public class TagQueryService {

//	@Autowired
//	private ProductCache productCache;
//
//	@Autowired
//	private TagTableService tagTableService;

//	public TagQueryResult tagQuery(HttpServletRequest request) {
//		TagQueryResult queryResult = new TagQueryResult();
//		TagQueryLogTO tagQueryLog = getTagQueryLog(request);
//		// TODO
//
//		long tagId = tagQueryLog.getTagBasicInfo().getMeta().getTagId();
//		ProductTO product = productCache.getProductById(tagQueryLog.getTagBasicInfo().getProductId());
//
//		// 更新标签访问日志
//		TagAccessLogEO accessLog = wrapTagAccessLog(tagQueryLog.getIpAddress());
//		tagTableService.addTagAccessLog(tagId, accessLog);
//
//		// 获取标签访问日志
//		List<TagAccessLogEO> accessLogs = tagTableService.getTagAccessLogs(tagId);
//
//		queryResult.setAccessLogs(accessLogs);
//		queryResult.setProduct(product);
//		queryResult.setTagQueryLog(tagQueryLog);
//		return queryResult;
//	}
//
//	/**
//	 * 获取扫码日志，用于日志分析系统
//	 */
//	private TagQueryLogTO getTagQueryLog(HttpServletRequest request) {
//
//		TagBasicInfoEO tagBasicInfo = (TagBasicInfoEO) request.getAttribute(PortalConstants.tagBasicInfoAttr);
//		if (tagBasicInfo == null) {
//			throw new PortalCustomException(TagQueryError.TagBasicInfoMissing);
//		}
//
//		TagQueryLogTO tagQueryLog = new TagQueryLogTO();
//		tagQueryLog.setQueryTime(new Date());
//		tagQueryLog.setTagBasicInfo(tagBasicInfo);
//		tagQueryLog.setIpAddress(ServletRequestHelper.getClientIp(request));
//		tagQueryLog.setUserAgent(request.getHeader("user-agent"));
//
//		return tagQueryLog;
//	}
//
//	private TagAccessLogEO wrapTagAccessLog(String ip) {
//		TagAccessLogEO accessLog = new TagAccessLogEO();
//
//		accessLog.setTime(new Date());
//		AddressEO address = new AddressEO();
//
//		JuheIPLocationResponse response = JuheIPLocationAPI.location(ip);
//		if (!response.validate()) {
//			// TODO
//			return null;
//		}
//		address.setFullAddress(response.getResult().getArea());
//		accessLog.setAddress(address);
//
//		return accessLog;
//	}
}
