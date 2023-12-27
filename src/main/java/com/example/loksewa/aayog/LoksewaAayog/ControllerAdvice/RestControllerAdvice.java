package com.example.loksewa.aayog.LoksewaAayog.ControllerAdvice;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.loksewa.aayog.LoksewaAayog.Exception.UserNotFoundException;

@ControllerAdvice
public class RestControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex){
		List <String> errors = ex.getBindingResult().getFieldErrors()
				.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST); 
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> error = new HashMap<>();
		error.put("errors", errors);
		return error;
		
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, List<String>>> handleNotFoundException(UserNotFoundException ex) {
	    List<String> errors = Collections.singletonList(ex.getMessage());
	    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
	    List<String> errors = Collections.singletonList(ex.getMessage());
	    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
	    List<String> errors = Collections.singletonList(ex.getMessage());
	    return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<Map<String, List<String>>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
