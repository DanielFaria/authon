package com.authon.oauth.client;


import javax.servlet.http.HttpServletRequest;

import com.authon.oauth.server.OAuth2Exception;

public class OAuthTokenRequest {
	public static String CLIENT_ID = "client_id";
	public static String REDIRECT_URI = "redirect_uri";
	public static String STATE = "state";
	public static String NONCE = "nonce";
	
    private Client clientInfo;
	private String nonce;
	private String aud;
	private String state;

	public OAuthTokenRequest(HttpServletRequest request){
        if(request == null){
            throw new OAuth2Exception("Request can`t be null");
        }
        String clientId = request.getParameter(CLIENT_ID);
        String redirectUrl = request.getParameter(REDIRECT_URI);
        String state = request.getParameter(STATE);
        String nonce = request.getParameter(NONCE);
        if( clientId == null || redirectUrl == null || state == null || nonce == null ){
            throw new OAuth2Exception("Parameters client_id, redirectUrl, state and nonce are required");
        }
        this.clientInfo = new Client(clientId, redirectUrl);
        this.nonce = nonce;
        this.state = state;
        this.aud = clientId;
    }

    public String getClientId(){
       return this.clientInfo.getClientId();
    }

    public String getRedirectUrl(){
	    return this.clientInfo.getRedirectUrl();
    }

    public Client getClientInfo() {
        return clientInfo;
    }

    public String getNonce() {
        return nonce;
    }

    public String getAud() {
        return aud;
    }

    public String getState() {
        return state;
    }
}
