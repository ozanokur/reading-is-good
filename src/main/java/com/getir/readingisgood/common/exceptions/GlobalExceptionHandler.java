package com.getir.readingisgood.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
 
	@ExceptionHandler
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleException(Exception ex) {
		return new ExceptionResponse(ex.getMessage());
	}
 
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
		StringBuilder builder = new StringBuilder();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			builder.append(errorMessage);
			builder.append(" ");
		});
		return new ExceptionResponse(builder.toString());
	}
}