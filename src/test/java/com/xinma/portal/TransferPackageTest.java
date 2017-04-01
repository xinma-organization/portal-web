package com.xinma.portal;

import org.junit.Test;

import com.xinma.base.core.model.TransPackageDTO;
import com.xinma.base.core.util.KeyHelper;

public class TransferPackageTest {

	@Test
	public void test() throws Exception {
		KeyHelper.initDigestKeyMap();
		
		String encodeString = TransPackageDTO.encodeMetadata("1234567890");
		
		String plainText = TransPackageDTO.decode(encodeString).getDecodeMetadata(String.class);
		
		System.out.println(plainText);
	}
}
