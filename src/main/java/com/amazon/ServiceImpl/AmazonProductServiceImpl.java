package com.amazon.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.DAO.AmazonProductRepository;
import com.amazon.ExceptionHandle.GlobalException;
import com.amazon.Model.AmazonProduct;
import com.amazon.Service.AmazonProductService;
import com.amazon.Util.Utility;

@Service
public class AmazonProductServiceImpl implements AmazonProductService {

	private static final Logger logger = LoggerFactory.getLogger(AmazonProductServiceImpl.class);

	@Autowired
	private AmazonProductRepository amazonProductRepository;

	@Transactional(rollbackOn = Exception.class)
	@Override
	public List<AmazonProduct> addProductFromProductmanufacturer(List<Map<String, Object>> list) {
		List<AmazonProduct> resultList = new ArrayList<>();
		logger.info("<---Response from ProductManufacturer--->" + list);
		list.parallelStream().forEach(product -> {
			AmazonProduct amazonProduct = new AmazonProduct();
			try {
				amazonProduct
						.setProductCode(Optional.of(product.get("productCode").toString()).orElseThrow(Exception::new));
				amazonProduct
						.setProductName(Optional.of(product.get("productName").toString()).orElseThrow(Exception::new));
				amazonProduct.setProductbrandName(
						Optional.of(product.get("productbrandName").toString()).orElseThrow(Exception::new));
				amazonProduct.setModelNo(Optional.of(product.get("modelNo").toString()).orElseThrow(Exception::new));
				amazonProduct.setSerialNo(Optional.of(product.get("serialNo").toString()).orElseThrow(Exception::new));
				amazonProduct.setTotalStock(Optional.of(Integer.parseInt(product.get("totalStock").toString()))
						.orElseThrow(Exception::new));
				amazonProduct.setAvaliableStock(Optional.of(Integer.parseInt(product.get("avaliableStock").toString()))
						.orElseThrow(Exception::new));
				amazonProduct.setIsActive('Y');
				amazonProduct.setExpDate(Optional.of(Utility.StringToDateConvert(product.get("expDate").toString()))
						.orElseThrow(Exception::new));
				amazonProduct.setMfgDate(Optional.of(Utility.StringToDateConvert(product.get("mfgDate").toString()))
						.orElseThrow(Exception::new));

				Double manufacturerPrice = Optional.of(Double.parseDouble(product.get("productprice").toString()))
						.orElseThrow(Exception::new);
				amazonProduct.setProductprice(Utility.roundAvoid(manufacturerPrice, 2));

				Double amazonPrice = Utility.amazonSellingPrice(manufacturerPrice);
				amazonProduct.setAmazonProductprice(Utility.roundAvoid(amazonPrice, 2));

				amazonProduct
						.setDescription(Optional.of(product.get("description").toString()).orElseThrow(Exception::new));
				AmazonProduct savedProduct = amazonProductRepository.save(amazonProduct);
				resultList.add(savedProduct);
			} catch (Exception e) {
				e.printStackTrace();
				throw new GlobalException("Server Error!! " + e.getMessage());
			}
		});
		logger.info("<---Amazon Saved Product's--->" + resultList);
		return resultList;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public AmazonProduct addAmazonProductDetails(AmazonProduct productDetailsRequest) {
		boolean isAvaliable = amazonProductRepository.existsByProductCodeOrSerialNoAndIsActive(
				productDetailsRequest.getProductCode(), productDetailsRequest.getSerialNo(), 'Y');
		if (isAvaliable) {
			throw new GlobalException("Product Details Are Already Avaliable");
		} else {
			AmazonProduct product = new AmazonProduct();
			product.setProductCode(productDetailsRequest.getProductCode());
			product.setProductName(productDetailsRequest.getProductName());
			product.setProductbrandName(productDetailsRequest.getProductbrandName());
			product.setModelNo(productDetailsRequest.getModelNo());
			product.setSerialNo(productDetailsRequest.getSerialNo());
			product.setTotalStock(productDetailsRequest.getTotalStock());
			product.setAvaliableStock(productDetailsRequest.getAvaliableStock());
			product.setIsActive('Y');
			product.setMfgDate(productDetailsRequest.getMfgDate());
			product.setExpDate(productDetailsRequest.getExpDate());
			product.setProductprice(0.00);
			product.setAmazonProductprice(productDetailsRequest.getAmazonProductprice());
			product.setDescription(productDetailsRequest.getDescription());
			logger.info("Amazon Product Detail:{}" + product);
			return amazonProductRepository.save(product);
		}
	}

	@Override
	public List<AmazonProduct> getAllAmazonProduct() {
		List<AmazonProduct> productlist = amazonProductRepository.findAll('Y');
		logger.info("<------Amazon Product Details List------>" + productlist);
		if (null != productlist && !productlist.isEmpty()) {
			Collections.sort(productlist, (p1, p2) -> p1.getProductprice().compareTo(p2.getProductprice()));
			return productlist;
		} else {
			throw new NullPointerException("Product Details Not Found");
		}
	}

	@Override
	public List<AmazonProduct> getAmazonProductBySerachKey(String searchKey) {
		List<AmazonProduct> productList = amazonProductRepository.findBySearchkey(searchKey, 'Y');
		if (null != productList && !productList.isEmpty()) {
			Collections.sort(productList, (p1, p2) -> p1.getProductprice().compareTo(p2.getProductprice()));
			return productList;
		} else {
			throw new NullPointerException("Products Details Not Found With Key: " + searchKey);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public AmazonProduct updateAmazonProductDetailsByProductcode(String productCode,
			AmazonProduct productDetailsRequest) {
		Optional<AmazonProduct> chkPoint = amazonProductRepository.findByProductCodeAndIsActive(productCode, 'Y');
		if (chkPoint.isPresent()) {
			AmazonProduct product = chkPoint.get();
			logger.info("Fetch Product Detail:{}" + product);
			product.setProductName(productDetailsRequest.getProductName());
			product.setProductbrandName(productDetailsRequest.getProductbrandName());
			product.setModelNo(productDetailsRequest.getModelNo());
			product.setTotalStock(productDetailsRequest.getTotalStock());
			product.setAvaliableStock(productDetailsRequest.getAvaliableStock());
			product.setMfgDate(productDetailsRequest.getMfgDate());
			product.setExpDate(productDetailsRequest.getExpDate());
			product.setAmazonProductprice(productDetailsRequest.getAmazonProductprice());
			product.setDescription(productDetailsRequest.getDescription());
			logger.info("Updated Product Detail:{}" + product);
			return amazonProductRepository.save(product);
		} else {
			throw new NullPointerException("Product Details Not Avaliable For productCode: " + productCode);
		}
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public boolean deleteAmazonProductByProductcode(String productcode) {
		boolean status = false;
		Optional<AmazonProduct> chkPoint = amazonProductRepository.findByProductCodeAndIsActive(productcode, 'Y');
		if (chkPoint.isPresent()) {
			AmazonProduct product = chkPoint.get();
			product.setIsActive('N');
			status = true;
		} else {
			throw new NullPointerException("Product Details Not Avaliable For productCode: " + productcode);
		}
		return status;
	}

}
