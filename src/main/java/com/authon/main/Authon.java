package com.authon.main;

import com.github.ulisesbocchio.spring.boot.security.saml.annotation.EnableSAMLSSO;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderConfigurerAdapter;
import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderBuilder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication(scanBasePackages = { "com.authon.controller", "com.authon.oauth.server","com.authon.oauth.server.token", "com.authon.oauth.server.policy"})
@EnableSAMLSSO
public class Authon {

    public static void main(String[] args) {
    	    SpringApplication.run(Authon.class, args);
    }

    @Configuration
    public static class MvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("index");
            registry.addViewController("/protected").setViewName("protected");
            registry.addViewController("/unprotected/help").setViewName("help");

        }
    }

    @Configuration
    public static class MyServiceProviderConfig extends ServiceProviderConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.authorizeRequests()
                    .antMatchers("/unprotected/**")
                    .permitAll()
                .and()
                    .anonymous();
            // @formatter:on
        }

        @Override
        public void configure(ServiceProviderBuilder serviceProvider) throws Exception {
            // @formatter:off
            serviceProvider
                .metadataGenerator()
                .entityId("https://authon.herokuapp.com/saml/metadata")
                .bindingsSSO("artifact", "post", "paos")
            .and()
                .ecpProfile()
            .and()
                .sso()
                .defaultSuccessURL("/home")
                //.idpSelectionPageURL("/idpselection")
            .and()
                .metadataManager()
                .metadataLocations("classpath:/idp-okta.xml")
                .refreshCheckInterval(0)
            .and()
                .keyManager()
                .privateKeyDERLocation("classpath:/localhost.key.der")
                .publicKeyPEMLocation("classpath:/localhost.cert");
            // @formatter:on

        }
    }
}
