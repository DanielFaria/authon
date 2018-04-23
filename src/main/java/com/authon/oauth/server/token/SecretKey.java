package com.authon.oauth.server.token;

import io.jsonwebtoken.impl.TextCodec;

public class SecretKey {
	
	private String originalSecret;
	private byte[] formattedKey;

	public SecretKey(String secretKey) {
		if(secretKey == null || secretKey.isEmpty())
			throw new RuntimeException("Secret Key can`t be null or empty");
		this.originalSecret = secretKey;
		this.formattedKey = this.formatKey(this.originalSecret);
	}

	private byte[] formatKey(String originalSecret) {
		return TextCodec.BASE64.decode(originalSecret);
	}

	public byte[] getKeyInBase64() {
		return this.formattedKey;
	}

}
