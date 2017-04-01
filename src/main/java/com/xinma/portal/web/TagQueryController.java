package com.xinma.portal.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinma.base.core.response.ResponseVO;
import com.xinma.base.tag.CloudTag;
import com.xinma.base.tag.TagParser;
import com.xinma.portal.log.PortalCustomException;
import com.xinma.portal.log.error.TagQueryError;
import com.xinma.tag.model.TagBasicInfoEO;
import com.xinma.tag.service.TagTableService;

@RestController
public class TagQueryController {

	Logger logger = LoggerFactory.getLogger(TagQueryController.class);
	
	private final static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private TagTableService tagTableService;

	@GetMapping(value = "{code}")
	public RedirectView tagQuery(@PathVariable String code, RedirectAttributes attributes) {
		
		ResponseVO responseVO = null;
		try {
			TagBasicInfoEO tagBasicInfo = tagCheck(code);
			
			responseVO = new ResponseVO<>(tagBasicInfo);
			
		} catch (PortalCustomException e) {
			
			responseVO = new ResponseVO<>(e.getError());
			logger.error("Exception in TagQueryController.tagQuery()", e);
			
		} catch (Throwable e) {
			responseVO = new ResponseVO<>(TagQueryError.TagQueryExceptionErr); 
			logger.error("Exception in TagQueryController.tagQuery(), code is  " + code, e);
		}
		
		attributes.addAttribute("param", "param");
		// TODO get template and redirect
		
		return new RedirectView("/tpl/default/index.html");
	}

	private TagBasicInfoEO tagCheck(String hiddenCode) throws Exception {

		CloudTag decodeTag = null;
		try {
			decodeTag = TagParser.decodeTagByHiddenCode(hiddenCode);
		} catch (Exception e) {
			throw new PortalCustomException(e, TagQueryError.TagDecodeErr, hiddenCode);
		}

		// get tag from ots and check
		TagBasicInfoEO tagBasicInfo = tagTableService.getTagBasicInfo(decodeTag.getTagId());
		if (tagBasicInfo != null) {
			if (!tagBasicInfo.getMeta().getHiddenCode().equals(decodeTag.getHiddenCode())
					|| !tagBasicInfo.getMeta().getHiddenRandomCode().equals(decodeTag.getHiddenRandomCode())
					|| !tagBasicInfo.getMeta().getCodeVersion().equals(decodeTag.getCodeVersion())) {

				String decodeTagStr = mapper.writeValueAsString(decodeTag);
				String tagBasicInfoStr = mapper.writeValueAsString(tagBasicInfo);

				throw new PortalCustomException(
						"decode tag is differ from tag in ots, decode tag is : " + decodeTagStr
								+ "ots tag basicinfo is : " + tagBasicInfoStr,
						TagQueryError.TagDifferFromCloudErr, decodeTagStr, tagBasicInfoStr);
			}
		} else {
			throw new PortalCustomException(
					"tag is not in ots, tag info is : " + mapper.writeValueAsString(decodeTag),
					TagQueryError.TagNotInCloudErr);
		}

		return tagBasicInfo;
	}
}
