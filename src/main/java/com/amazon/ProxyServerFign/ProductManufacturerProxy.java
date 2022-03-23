package com.amazon.ProxyServerFign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.amazon.Response.ProductManufacturerResponse;

//@FeignClient(name="${product.service.name}",url ="localhost:8080/v1/productapi")
//@FeignClient(name="${product.service.name}")
//@RibbonClient(name="${product.service.name}")
@FeignClient(name="ProductManufacturer",url ="localhost:8080/v1/productmanufacture")
public interface ProductManufacturerProxy {

	@GetMapping("/get-all-productdetails")
	public ProductManufacturerResponse getProductDetailsFromManufacturer();
}
