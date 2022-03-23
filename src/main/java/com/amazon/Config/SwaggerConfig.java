package com.amazon.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = new Contact("AmazonProductData",
			"http://localhost:7070/v1/amazonproductapi", "saurav.tit@gmail.com");
	public static final ApiInfo API_DEFAULT = new ApiInfo("AmazonProductData Api Documentation",
			"API Documentation Of AmazonProductData", "1.0", "urn:tos", DEFAULT_CONTACT, "AmazonProductAPI 2.0",
			"www.amazonproduct.com", new ArrayList<VendorExtension>());

	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));

	// @ http://localhost:8080/swagger-ui.html#
	// @ http://localhost:8080/v2/api-docs
	@Bean
	public Docket swagerAPI() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(API_DEFAULT).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
}
