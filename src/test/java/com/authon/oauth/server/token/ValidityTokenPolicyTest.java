package com.authon.oauth.server.token;

import org.junit.Test;

public class ValidityTokenPolicyTest {
	
	@Test(expected=RuntimeException.class)
	public void experitationInMinutesCantBeNegative() {
		new ValidityTokenPolicy(-1);
	}
    
	@Test(expected=RuntimeException.class)
	public void validityInMinutesCantBeBiggerThan60Minutes() {
		new ValidityTokenPolicy(61);
	}
	
	@Test
	public void validityTokenMustCreateWithMinutesUntil60() {
		new ValidityTokenPolicy(60);
	}
	
}
