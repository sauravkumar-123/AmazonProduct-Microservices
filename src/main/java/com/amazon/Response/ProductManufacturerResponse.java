package com.amazon.Response;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductManufacturerResponse {

	private ZonedDateTime timestamp;
	private boolean status;
	private String message;
	private Object datasource;
}
