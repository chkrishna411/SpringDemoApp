package com.mycompany.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/**
 * Track API Time taken
 * 
 * @author simham
 *
 */
@Component
public class AuditInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		long startTime = (Long)request.getAttribute("startTime");
		
		long endTime = System.currentTimeMillis();
		
		long totalTimeTaken = endTime - startTime;
		
		logger.info("Time taken for api: " + request.getRequestURI() + " call (ms): "+totalTimeTaken);
		
		super.postHandle(request, response, handler, modelAndView);
	}

	
	
	
	
	
	
	
}
