package com.authon.saml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

@Configuration
public class CustomFilter extends GenericFilterBean {
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		this.list(request, response);
		chain.doFilter(request, response);
		
	}

	private void list(ServletRequest req, ServletResponse res) {
		HttpServletRequest request = (HttpServletRequest) req;
      	Map<String, String> map = new HashMap<String, String>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
            System.out.println("Key:"+key+ " Valor:"+value);
        }
    	    System.out.println("oi"+map);
        
		
		
	}
}