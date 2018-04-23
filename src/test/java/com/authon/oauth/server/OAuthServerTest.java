package com.authon.oauth.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.authon.oauth.client.OAuthTokenRequest;
import com.authon.oauth.server.policy.PolicyOAuthServer;
import com.authon.oauth.server.token.IsRequestWithId;
import com.authon.oauth.server.token.SecretKey;
import com.authon.oauth.server.token.TokenProvider;
import com.authon.oauth.server.token.ValidityTokenPolicy;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.util.HashMap;
import java.util.Map;

public class OAuthServerTest {
	
	private OAuthServer aouthServer;
	private TokenProvider tokenProvider;
	private SecretKey secretKey;
	
	@Before
	public void init() {
	  ValidityTokenPolicy validityToken = new ValidityTokenPolicy(60);
	  secretKey = new SecretKey("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");
	  String issuer = "https://authon.herokuapp.com/oauth2/default";
	  this.tokenProvider = new TokenProvider(validityToken, secretKey, issuer);
	  this.aouthServer = new OAuthServer(tokenProvider, new PolicyOAuthServer());
	}
	
	@Test
	public void withValidRequestAValidTokenMustBeInRedirectUrlAsParemeter() {
		ResponseEntity<Object> response = this.aouthServer.buildAuthAndRedirectToClient(this.builValidRequest());
		String urlRedirect = response.getHeaders().getLocation().toString();
		Map<String, Object> parameters = this.extractParametersFrom(urlRedirect);
		String token = (String)parameters.get("access_token");
	    assertEquals(true, this.validaToken(token));
	}
	
	@Test
	public void withValidRequestValidParametersMustBeContainsInRedirectUrl() {
		ResponseEntity<Object> response = this.aouthServer.buildAuthAndRedirectToClient(this.builValidRequest());
		String urlRedirect = response.getHeaders().getLocation().toString();
		Map<String, Object> parameters = this.extractParametersFrom(urlRedirect);
	    assertEquals(parameters.get("state"), "afgafsretre6");
	    assertEquals(parameters.get("token_type"), "Bearer");
	    assertEquals(parameters.get("expires_in"), "3600");
	}
	
	@Test(expected=OAuth2Exception.class)
	public void whenClientIsUNAuthorizedExceptionMustBeThrown() {
		PolicyOAuthServer policyOAuthServer = mock(PolicyOAuthServer.class);
		when(policyOAuthServer.isAuthorized(any(OAuthTokenRequest.class))).thenReturn(false);
		OAuthServer oAuthServer = new OAuthServer(tokenProvider, policyOAuthServer);
		oAuthServer.buildAuthAndRedirectToClient(this.builValidRequest());
		
	}
	
	private Map<String, Object> extractParametersFrom(String urlRedirect) {
		urlRedirect = urlRedirect.replace("http://localhost:3000/implicit/callback?#", "");
		String[] keyValuePairs = urlRedirect.split("&");
		Map<String, Object> map = new HashMap<String, Object>();

		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			if (entry != null && entry.length == 2) {
				map.put(entry[0].trim(), entry[1].trim());
			}
		}
		return map;
	}

	private HttpServletRequest builValidRequest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.CLIENT_ID)))).thenReturn("0oadqodmkcWJbHQIN0h7");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.REDIRECT_URI)))).thenReturn("http://localhost:3000/implicit/callback");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.STATE)))).thenReturn("afgafsretre6");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.NONCE)))).thenReturn("89829828298298");
		return request;
	}
	
	public boolean validaToken(String token) {
		boolean validToken = true;
		try {
			Jwts.parser().setSigningKey(secretKey.getKeyInBase64()).parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			validToken = false;
			e.printStackTrace();
		}
		return validToken;

	}
	
	
}
