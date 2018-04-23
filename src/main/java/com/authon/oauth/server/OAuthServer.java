package com.authon.oauth.server;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import com.authon.oauth.client.OAuthTokenRequest;
import com.authon.oauth.server.policy.PolicyOAuthServer;
import com.authon.oauth.server.token.TokenProvider;
import com.authon.saml.SamlUserInfo;
import com.authon.saml.UserSAML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OAuthServer {
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private PolicyOAuthServer policyOAuthServer;
    
	public OAuthServer(TokenProvider tokenProvider, PolicyOAuthServer policyOAuthServer) {
		super();
		this.tokenProvider = tokenProvider;
		this.policyOAuthServer = policyOAuthServer;
	}

	public ResponseEntity<Object> buildAuthAndRedirectToClient(HttpServletRequest request) {
        OAuthTokenRequest oauthTokenRequest = new OAuthTokenRequest(request);
        if(!this.policyOAuthServer.isAuthorized(oauthTokenRequest)){
            throw new OAuth2Exception("Unauthorized request");
        }
        SamlUserInfo samlUserInfo = UserSAML.extractUserInfoIn(SecurityContextHolder.getContext().getAuthentication());
		String bearerToken = tokenProvider.createToken(oauthTokenRequest, samlUserInfo);
		String urlCallback = oauthTokenRequest.getRedirectUrl() + "?#id_token=" + bearerToken + "&access_token=" + bearerToken +
                "&&token_type=Bearer&expires_in=3600&scope=openid+email+profile&state=" + oauthTokenRequest.getState();
		return this.redirectToClient(urlCallback);
	}
	
	private ResponseEntity<Object> redirectToClient(String urlCallback) {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			URI redirect = new URI(urlCallback);
			httpHeaders.setLocation(redirect);
			return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
		} catch (URISyntaxException e) {
			throw new OAuth2Exception("Internal error at redirect to client" );
		}
	}
}
