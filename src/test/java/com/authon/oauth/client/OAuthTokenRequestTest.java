package com.authon.oauth.client;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import com.authon.oauth.server.OAuth2Exception;
import com.authon.oauth.server.token.IsRequestWithId;

public class OAuthTokenRequestTest {
	
	@Test(expected=OAuth2Exception.class)
	public void whenRequestDoesntHaveAllParameterExceptionMustBeThrow() {
	   HttpServletRequest request = this.buildInvalidRequest();
	   new OAuthTokenRequest(request);
	}
	
	@Test(expected=OAuth2Exception.class)
	public void whenRequestIsNullExceptionMustBeThrow() {
	   HttpServletRequest request = null;
	   new OAuthTokenRequest(request);
	}
	
	@Test
	public void whenRequestIsValidAOAuthRequestMustBeCreated() {
	   HttpServletRequest request = this.buildValidRequest();
	   OAuthTokenRequest oauthTokenRequest =  new OAuthTokenRequest(request);
	   assertEquals("client_id must be equal 0oadqodmkcWJbHQIN0h7", oauthTokenRequest.getClientId(), "0oadqodmkcWJbHQIN0h7" );
	   assertEquals("REDIRECT_URI must be equal http:localhost:8080/client/implicit/callback", oauthTokenRequest.getRedirectUrl(), "http:localhost:8080/client/implicit/callback" );
	   assertEquals("State must be equal afgafsretre6",oauthTokenRequest.getState(), "afgafsretre6" );
	   assertEquals("Nonce must be equal 89829828298298",oauthTokenRequest.getNonce(), "89829828298298" );
	   assertEquals("aud must be equal 0oadqodmkcWJbHQIN0h7",oauthTokenRequest.getAud(), "0oadqodmkcWJbHQIN0h7" );
	   Client clientInfo = new Client("0oadqodmkcWJbHQIN0h7", "http:localhost:8080/client/implicit/callback");
	   assertEquals(clientInfo, oauthTokenRequest.getClientInfo());
	   assertEquals("aud must be equal 0oadqodmkcWJbHQIN0h7",oauthTokenRequest.getAud(), "0oadqodmkcWJbHQIN0h7" );
	}
	
	private HttpServletRequest buildInvalidRequest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getParameter(any(String.class))).thenReturn(null);
		return request;
	}
	
	private HttpServletRequest buildValidRequest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.CLIENT_ID)))).thenReturn("0oadqodmkcWJbHQIN0h7");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.REDIRECT_URI)))).thenReturn("http:localhost:8080/client/implicit/callback");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.STATE)))).thenReturn("afgafsretre6");
		Mockito.when(request.getParameter(Mockito.argThat(new IsRequestWithId(OAuthTokenRequest.NONCE)))).thenReturn("89829828298298");
		return request;
	}
}
