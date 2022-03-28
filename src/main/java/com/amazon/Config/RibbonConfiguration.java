package com.amazon.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;

public class RibbonConfiguration {

	@Autowired
	IClientConfig iClientConfig;
	
//	@Bean
//	public IPing ping(IClientConfig ribbonclient) {
//		return new PingUrl();
//	}
	
	@Bean
	public IRule rule(IClientConfig ribbonclient) {
		return new AvailabilityFilteringRule();
	}
}
