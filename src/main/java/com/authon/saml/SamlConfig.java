package com.authon.saml;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilder;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderConfigurerAdapter;

@Configuration
public class SamlConfig extends ServiceProviderConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers("/").permitAll().and().anonymous();
		// @formatter:on
	}

	@Override
	public void configure(ServiceProviderBuilder serviceProvider) throws Exception {
		// @formatter:off
		serviceProvider.metadataGenerator().entityId("localhost-demo").bindingsSSO("artifact", "post", "paos").and()
				.ecpProfile().and().sso().defaultSuccessURL("/home")
				// .idpSelectionPageURL("/idpselection")
				.and().metadataManager().metadataLocations("classpath:/idp-okta.xml").refreshCheckInterval(0).and()
				.extendedMetadata().ecpEnabled(true).idpDiscoveryEnabled(false)// set to false for no IDP Selection
																				// page.
				.and().keyManager().privateKeyDERLocation("classpath:/localhost.key.der")
				.publicKeyPEMLocation("classpath:/localhost.cert");
		// @formatter:on

	}
}
