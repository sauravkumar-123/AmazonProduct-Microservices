package com.amazon.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.amazon.Model.AmazonProduct;

@Service
public interface AmazonProductService {

	public List<AmazonProduct> addProductFromProductmanufacturer(List<Map<String, Object>> list);

	public AmazonProduct addAmazonProductDetails(AmazonProduct productDetailsRequest);

	public List<AmazonProduct> getAllAmazonProduct();

	public List<AmazonProduct> getAmazonProductBySerachKey(String searchKey);

	public AmazonProduct getAmazonProductByProductCode(String productCode);

	public AmazonProduct updateAmazonProductDetailsByProductcode(String productcode,
			AmazonProduct productDetailsRequest);

	public boolean deleteAmazonProductByProductcode(String productcode);
}
