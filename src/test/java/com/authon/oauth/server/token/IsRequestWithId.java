package com.authon.oauth.server.token;

import org.mockito.ArgumentMatcher;

public class IsRequestWithId extends ArgumentMatcher<String> {
	private final String id;

	public IsRequestWithId(String id) {
		this.id = id;
	}

	public boolean matches(Object arg) {
		if (arg instanceof String) {
			String parameter = (String) arg;
			return parameter.equals(this.id);
		}else {
			return false;
		}
	}
}
