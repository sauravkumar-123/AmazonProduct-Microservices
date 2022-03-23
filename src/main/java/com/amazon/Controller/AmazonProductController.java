package com.amazon.Controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ExceptionHandle.GlobalException;
import com.amazon.Model.AmazonProduct;
import com.amazon.ProxyServerFign.ProductManufacturerProxy;
import com.amazon.Response.AmazonProductResponse;
import com.amazon.Response.ProductManufacturerResponse;
import com.amazon.Service.AmazonProductService;

import io.swagger.annotations.Api;

@Api(value = "AmazonProductController", description = "This is AmazonProduct Controller for communicate with ProductManufacturer API")
@RestController
@RequestMapping(value = "/v1/amazonproductapi")
public class AmazonProductController {

	private static final Logger logger = LoggerFactory.getLogger(AmazonProductController.class);

	@Autowired
	private AmazonProductService productService;

	@Autowired
	private ProductManufacturerProxy proxyserverdata;

	@GetMapping("/get-product-from-manufacturer")
	public ResponseEntity<AmazonProductResponse> getProductDetailsFromManufacturer() {

		ProductManufacturerResponse manufacturerResponse = proxyserverdata.getProductDetailsFromManufacturer();
		if (null != manufacturerResponse && manufacturerResponse.isStatus()) {
			try {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> productList = (List<Map<String, Object>>) manufacturerResponse
						.getDatasource();
				logger.info("<---Response from ProductManufacturer--->" + productList);
				if (null != productList && !productList.isEmpty()) {
					List<AmazonProduct> amazonProduct = productService.addProductFromProductmanufacturer(productList);
					return new ResponseEntity<AmazonProductResponse>(
							new AmazonProductResponse(true, "Product Details Saved From Manufacturer", amazonProduct),
							HttpStatus.CREATED);
				} else {
					throw new NullPointerException("Data Not Found from productManufacturer server");
				}
				/*******************************************************************
				 * JSONArray json_arr=new JSONArray(); JSONObject json_obj=new JSONObject(); for
				 * (Map<String, Object> map : list) { for (Map.Entry<String, Object> entry :
				 * map.entrySet()) { String key = entry.getKey(); Object value =
				 * entry.getValue(); try { json_obj.put(key,value); } catch (JSONException e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); } }
				 * json_arr.put(json_obj); }
				 *********************************************************************/
			} catch (Exception e) {
				e.printStackTrace();
				throw new GlobalException("Server Error!!! while fetch data from productManufacturer server");
			}
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Data Not Found From ProductManufacturer!!Please Check", null),
					HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add-productdetails")
	public ResponseEntity<AmazonProductResponse> addProductDetails(
			@Valid @ModelAttribute AmazonProduct productDetailsRequest) {
		logger.info("<---Request Payload--->" + productDetailsRequest);
		AmazonProduct amazonProduct = productService.addAmazonProductDetails(productDetailsRequest);
		if (null != amazonProduct) {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(true, "amazon Product Detail Saved", amazonProduct), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Unable To Save flipcart Product Detail", amazonProduct),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-all-productdetails")
	public ResponseEntity<AmazonProductResponse> getProductDetails() {
		List<AmazonProduct> prodList = productService.getAllAmazonProduct();
		logger.info("<-----amazon Product List----->" + prodList);
		if (null != prodList && !prodList.isEmpty()) {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(true, "Product Details List Fetched", prodList), HttpStatus.OK);
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Unable To Fetch Product Details List", prodList),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/get-productdetailsByKey")
	public ResponseEntity<AmazonProductResponse> getProductBySearchkey(@RequestParam("searchKey") String searchKey) {
		logger.info("<---Search Key--->" + searchKey);
		List<AmazonProduct> prodList = productService.getAmazonProductBySerachKey(searchKey);
		logger.info("<-----Product List----->" + prodList);
		if (null != prodList && !prodList.isEmpty()) {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(true, "Product Details Fetched", prodList), HttpStatus.OK);
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Unable To Fetch Product Details", prodList),
					HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update-productdetails/{productCode}")
	public ResponseEntity<AmazonProductResponse> updateProductdetailsByProductcode(
			@PathVariable("productCode") String productCode,
			@Valid @ModelAttribute AmazonProduct productDetailsRequest) {

		logger.info("<---ProductCode And Request Payload--->" + productCode + " " + productDetailsRequest);
		AmazonProduct product = productService.updateAmazonProductDetailsByProductcode(productCode,
				productDetailsRequest);
		logger.info("<-----Updated Product Detail----->" + product);
		if (null != product) {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(true, "Product Details Updated Successfully", product), HttpStatus.OK);
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Unable To Update Product Details", product),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete-productdetails")
	public ResponseEntity<AmazonProductResponse> deleteProductdetailsByProductcode(
			@RequestParam("productCode") String productCode) {
		logger.info("<---ProductCode --->" + productCode);
		boolean status = productService.deleteAmazonProductByProductcode(productCode);
		if (status) {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(true, "Product Details Deleted Successfully", null), HttpStatus.OK);
		} else {
			return new ResponseEntity<AmazonProductResponse>(
					new AmazonProductResponse(false, "Unable To Delete Product Details", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
