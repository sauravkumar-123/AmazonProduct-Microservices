package com.amazon.ExceptionHandle;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ExceptionResponse> handleAnyException(Exception exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ZonedDateTime.now(), false, exception.getMessage(),
				null);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { NullPointerException.class })
	public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException exception) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(ZonedDateTime.now(), false, exception.getMessage(),
				null);
		return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { MaxUploadSizeExceededException.class })
	public ResponseEntity<Object> handleMaxSizeFileUploadException(MaxUploadSizeExceededException exception) {
		ExceptionResponse response = new ExceptionResponse(ZonedDateTime.now(), false, "Maximum Uplaod Size is 500kb",
				null);
		return new ResponseEntity<Object>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(value = { BindException.class })
	protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
//		String fieldError= exception.getBindingResult().getFieldError().getDefaultMessage();
//		ExceptionResponce response=new ExceptionResponce(LocalDateTime.now(),false,fieldError);
		Map<String, String> errors = new HashMap<String, String>();
		exception.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		ExceptionResponse response = new ExceptionResponse(ZonedDateTime.now(), false, "Validation Error!!", errors);
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		// log.error(INVALID_REQUEST, ex);
		Map<String, Object> errors = new HashMap<>();
		if (!ex.getConstraintViolations().isEmpty()) {
//			ex.getConstraintViolations().forEach((error) -> {
//				String fieldName = null;
//				error.getPropertyPath().forEach((node) -> {
//					
//					fieldName = node.getName();
//				});
//				errors.put(fieldName, error.getMessage());
//			});
			for (ConstraintViolation constraintViolation : ex.getConstraintViolations()) {
				String fieldName = null;
				for (Node node : constraintViolation.getPropertyPath()) {
					fieldName = node.getName();
				}
				errors.put(fieldName, constraintViolation.getMessage());
			}
		}
		ExceptionResponse response = new ExceptionResponse(ZonedDateTime.now(), false, "Validation Error!!", errors);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}
}
