package com.authon.oauth.server.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenProviderBuilder {
	
	@Value("${oauth.server.issuer}")
	private String issuer;
	
	@Value("${oauth.token.validity.in.minutes}")
	private int validityTokenInMinutes;
	
	@Value("${oauth.privatekey.to.signing.token}")
	private String secret;
	
	@Bean
	public TokenProvider build() {
		ValidityTokenPolicy validityToken = new ValidityTokenPolicy(validityTokenInMinutes);
		SecretKey secretKey = new SecretKey(secret);
		return new TokenProvider(validityToken, secretKey, issuer);
		
	}

}
