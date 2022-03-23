package com.amazon.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AmazonProductResponse {

	private boolean status;
	private String message;
	private Object datasource;
}
