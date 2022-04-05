package com.amazon.ProxyServerFign;

import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.amazon.ExceptionHandle.GlobalException;
import com.amazon.Response.ProductManufacturerResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class ProductManufacturerProxyImpl implements ProductManufacturerProxy {

	private static final Logger logger = LoggerFactory.getLogger(ProductManufacturerProxyImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(commandKey = "amazonProduct", groupKey = "amazonProduct", fallbackMethod = "getProductDetailsFromManufacturerFallback")
	public ProductManufacturerResponse getProductDetailsFromManufacturer() {
		final String URL = "http://ProductManufacturer/v1/productmanufacture/get-all-productdetails";
		try {
			ProductManufacturerResponse ManufacturerResponse = restTemplate.getForObject(URL,ProductManufacturerResponse.class);
			logger.info("Responce From Manufacturer:{}" + ManufacturerResponse);
			return ManufacturerResponse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GlobalException("Server Error!!While Calling ProductManufacturer");
		}
	}

	public ProductManufacturerResponse getProductDetailsFromManufacturerFallback() {
		ProductManufacturerResponse maufacturerResponse = new ProductManufacturerResponse(ZonedDateTime.now(), false,
				"ProductManufacturer Service Gateway Not Respond!!Try Again After Some Time", null);
		return maufacturerResponse;
	}
}
