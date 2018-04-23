package com.authon.oauth.client;

import java.util.Objects;

public class Client {
	  
	private String clientId;
	private String redirectUrl;

	public Client(String clientId, String redirectUrl) {
		super();
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;

	}

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(getClientId(), client.getClientId()) &&
                Objects.equals(getRedirectUrl(), client.getRedirectUrl());
    }

    @Override
    public int hashCode() {
	    return Objects.hash(getClientId(), getRedirectUrl());
    }

    @Override
	public String toString() {
		return "Client [clientId=" + clientId + ", redirectUrl=" + redirectUrl + "]";
	}
	
}
