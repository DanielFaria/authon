package com.authon.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.authon.oauth.server.OAuthServer;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {
	
	@Autowired
	private OAuthServer oauthServer;

    public OAuthController(OAuthServer oauthServer) {
		super();
		this.oauthServer = oauthServer;
	}
    
	@RequestMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}
   
	@RequestMapping("/oauth2/default/v1/authorize")
	public ResponseEntity<Object> buildOAuthToken(HttpServletRequest request, HttpServletResponse response){
	    return oauthServer.buildAuthAndRedirectToClient(request);
	}
	
   @RequestMapping("implicit/callback")
   public void getInfo(HttpServletRequest request, HttpServletResponse response){
    }	
	
   

	
}