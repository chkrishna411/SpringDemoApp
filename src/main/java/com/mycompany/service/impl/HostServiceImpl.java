package com.mycompany.service.impl;

import org.springframework.stereotype.Component;

import com.mycompany.service.HostService;
import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HostServiceImpl implements HostService {

	public Map<String, String> getServerInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<>();
		try {
			map.put("server", request.getServerName());
			map.put("port", String.valueOf(request.getServerPort()));

			if (InetAddress.getLocalHost() != null) {

				map.put("hostAddress", InetAddress.getLocalHost().getHostAddress());
				map.put("hostName", InetAddress.getLocalHost().getHostName());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return map;
	}
}
