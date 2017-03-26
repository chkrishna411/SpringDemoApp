package com.mycompany.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface HostService {

	
	 Map<String, String> getServerInfo(HttpServletRequest request);
}
