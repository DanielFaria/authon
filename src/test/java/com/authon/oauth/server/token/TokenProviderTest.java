package com.authon.oauth.server.token;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.authon.oauth.client.OAuthTokenRequest;
import com.authon.saml.SamlUserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static org.junit.Assert.*;

public class TokenProviderTest {
	
	private TokenProvider tokenProvider;
	private SamlUserInfo samlUser;
	private SecretKey secretKey = new SecretKey("OnUddiMyGGxHT7O7B90W_KG0INPTc5xIvR8nFKg4");
	private OAuthTokenRequest oauthTokenRequest;
	private HttpServletRequest request;
	private ValidityTokenPolicy validityToken;
	
	@Before
	public void init() {
		samlUser = new SamlUserInfo("teste@gmail.com", "Teste");
		validityToken = new ValidityTokenPolicy(60);
		tokenProvider = new TokenProvider(validityToken, secretKey, "https://authon.herokuapp.com/oauth2/default" );
		request = this.builValidRequest();
		oauthTokenRequest = new OAuthTokenRequest(request);
	}
    
	@Test
	public void testTokenMustValidWhenParsingWithRightSigningKey() {
		String token = tokenProvider.createToken(oauthTokenRequest, samlUser);
		boolean validToken = true;
		try {
			Jwts.parser().setSigningKey(secretKey.getKeyInBase64()).parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			validToken = false;
			e.printStackTrace();
		}
		assertEquals(true, validToken);

	}
    
	@Test
	public void testTokenMustInvalidValidWhenParsingWithRightSigningKey() {
		String token = tokenProvider.createToken(oauthTokenRequest, samlUser);
		boolean validToken = true;
		SecretKey otherSecretKey = new SecretKey("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E");
		try {
			Jwts.parser().setSigningKey(otherSecretKey.getKeyInBase64()).parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			validToken = false;
			
		}
		assertEquals(false, validToken);
	}
    
	@Test
	public void testValidTokenMustHasCorrectClaims() {
		String token = tokenProvider.createToken(oauthTokenRequest, samlUser);
		Claims claims = Jwts.parser().setSigningKey(this.secretKey.getKeyInBase64()).parseClaimsJws(token)
				.getBody();
		assertEquals("aud = 0oadqodmkcWJbHQIN0h7  must be inside the token ", claims.get("aud"), "0oadqodmkcWJbHQIN0h7" );   
		assertEquals("iss = https://authon.herokuapp.com/oauth2/default  must be inside the token ", claims.get("iss"), "https://authon.herokuapp.com/oauth2/default" );
		assertEquals("nonce = 89829828298298  must be inside the token ", claims.get(OAuthTokenRequest.NONCE), "89829828298298" );   
	}
	
	@Test
	public void testValidTokenMustHasEmailAndNameOfUserInClaims() {
		String token = tokenProvider.createToken(oauthTokenRequest, samlUser);
		Claims claims = Jwts.parser().setSigningKey(this.secretKey.getKeyInBase64()).parseClaimsJws(token)
				.getBody();
		assertEquals("name = Teste  must be inside the token ", claims.get("name"), "Teste" );   
		assertEquals("email = teste@gmail.com  must be inside the token ", claims.get("email"), "teste@gmail.com" );   
	}
	
	
	private HttpServletRequest builValidRequest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.CLIENT_ID)))).thenReturn("0oadqodmkcWJbHQIN0h7");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.REDIRECT_URI)))).thenReturn("http:localhost:8080/client/implicit/callback");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.STATE)))).thenReturn("afgafsretre6");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.NONCE)))).thenReturn("89829828298298");
		return request;
	}
	
}



