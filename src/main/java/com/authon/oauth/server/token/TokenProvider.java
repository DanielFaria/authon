package com.authon.oauth.server.token;

import java.util.HashMap;
import java.util.Map;

import com.authon.oauth.client.OAuthTokenRequest;
import com.authon.saml.SamlUserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenProvider {

	private static String SUBJECT = "Authon";

	private ValidityTokenPolicy validityToken;

	private SecretKey secretKey;

	private String issuer;

	public TokenProvider(ValidityTokenPolicy validityToken, SecretKey secretKey, String issuer) {
		super();
		this.validityToken = validityToken;
		this.secretKey = secretKey;
		this.issuer = issuer;
	}

	public String createToken(OAuthTokenRequest request, SamlUserInfo samlUserInfo) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nonce", request.getNonce());
		parameters.put("iss", this.issuer);
		parameters.put("aud", request.getClientId());
		parameters.put("email", samlUserInfo.getEmail());
		parameters.put("name", samlUserInfo.getName());
		return Jwts.builder().setSubject(SUBJECT).setClaims(parameters)
				.signWith(SignatureAlgorithm.HS256, this.secretKey.getKeyInBase64())
				.setIssuedAt(this.validityToken.getIssuerDate()).setExpiration(this.validityToken.getExpirationDate())
				.compact();

	}

}