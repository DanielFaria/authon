package com.authon.oauth.server.token;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import io.jsonwebtoken.impl.TextCodec;

public class SecretKeyTest {
    
	private String ORIGINAL_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
	
	@Test(expected = RuntimeException.class)
	public void testStringKeyCantBeNull() {
		new SecretKey(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void testStringKeyCantBeEmpty() {
		new SecretKey("");
	}
	
	@Test
	public void testSecretKeyMustBeCreatedWithValidParameter() {
		new SecretKey(ORIGINAL_KEY);
	}
	
	@Test
	public void testSecretKeyMustBeReturnKeyInRightFormat() {
		SecretKey secretKey = new SecretKey(ORIGINAL_KEY);
		byte [] formattedKey = TextCodec.BASE64.decode(ORIGINAL_KEY);
		assertEquals(true, Arrays.equals(formattedKey, secretKey.getKeyInBase64()));
		
		
	}
}
