package com.xinma.portal.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

	@Autowired
	private TagTableService tagTableService;

	@GetMapping(value = "{code}")
	public String tagQuery(@PathVariable String code, HttpServletRequest request) {
		ResponseVO responseVO = null;
		try {
			TagBasicInfoEO tagBasicInfo = tagCheck(code);
			ResponseVO responseVO = new ResponseVO<T>(result);
		} catch (PortalCustomException e) {
			ResponseVO responseVO = new ResponseVO<>(error);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

				ObjectMapper mapper = new ObjectMapper();
				String decodeTagStr = mapper.writeValueAsString(decodeTag);
				String tagBasicInfoStr = mapper.writeValueAsString(tagBasicInfo);

				throw new PortalCustomException(
						"decode tag is differ from tag in ots, decode tag is : " + decodeTagStr
								+ "ots tag basicinfo is : " + tagBasicInfoStr,
						TagQueryError.TagDifferFromCloudErr, decodeTagStr, tagBasicInfoStr);
			}
		} else {
			throw new PortalCustomException(
					"tag is not in ots, tag info is : " + new ObjectMapper().writeValueAsString(decodeTag),
					TagQueryError.TagNotInCloudErr);
		}

		return tagBasicInfo;
	}
}
