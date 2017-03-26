package com.mycompany.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.service.HostService;


@RestController
public class HostDetailsController {

	@Autowired
	private HostService hostService;
	
	@RequestMapping("/host")
	public Map<String,String> getHostDetails(HttpServletRequest request) {
		
			
		return this.hostService.getServerInfo(request);
		
		
	}
}
