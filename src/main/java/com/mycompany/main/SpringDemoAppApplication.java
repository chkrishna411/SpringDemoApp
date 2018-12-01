package com.mycompany.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;

@SpringBootApplication
@ComponentScan("com.mycompany")
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class SpringDemoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoAppApplication.class,args);
	}
}
