package com.authon.oauth.server.policy;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.authon.oauth.client.Client;
import com.authon.oauth.client.OAuthTokenRequest;

@Configuration
public class PolicyOAuthServer {
	
	private static Set<Client> authorizedClients = new HashSet<Client>();
	
	static {
		authorizedClients.add(new Client("0oadqodmkcWJbHQIN0h7", "http://localhost:3000/implicit/callback"));
	}

	public boolean isAuthorized(OAuthTokenRequest authTokenRequest){
		return authorizedClients.contains(authTokenRequest.getClientInfo());
	}
	
	@Bean
	public PolicyOAuthServer build() {
		return new PolicyOAuthServer();
	}
}
