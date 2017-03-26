package com.mycompany.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Configure Interceptor
 * 
 * @author simham
 *
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {

	@Autowired
	private AuditInterceptor auditInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(auditInterceptor);
	}

	
	
	
	
	
}
