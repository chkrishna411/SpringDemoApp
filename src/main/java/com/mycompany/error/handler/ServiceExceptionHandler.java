package com.mycompany.error.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mycompany.domain.ErrorResponse;
import com.mycompany.exception.InvalidException;



@EnableWebMvc
@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handlerControllerException(HttpServletRequest req, Throwable ex) {
		logger.info("Central exception handling preparing the error response");
		ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error", "Server error Occurred");
		if(ex instanceof InvalidException) {
			InvalidException exception = (InvalidException)ex;
			errorResponse = new ErrorResponse(exception.getStatusCode(), exception.getErrorCode(), exception.getErrorMessage());
			return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(exception.getStatusCode()));
		}
		
		return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
		
	}
}
