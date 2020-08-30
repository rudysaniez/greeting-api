package com.me.work.greeting.api.controller.handler.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import com.me.work.greeting.api.exception.InvalidInputException;
import com.me.work.greeting.api.exception.NotFoundException;
import com.me.work.greeting.api.model.HttpErrorInfo;

@RestControllerAdvice
public class MainControllerExceptionHandler {

	/**
	 * @param ex
	 * @return {@link HttpErrorInfo}
	 */
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = NotFoundException.class)
	public @ResponseBody HttpErrorInfo notFound(NotFoundException ex) {
		return this.createHttpError(ex, HttpStatus.NOT_FOUND);
	}
	
	/**
	 * @param ex
	 * @return {@link HttpErrorInfo}
	 */
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(value = UnprocessableEntity.class)
	public @ResponseBody HttpErrorInfo invalidInput(InvalidInputException ex) {
		return this.createHttpError(ex, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * @param ex
	 * @param status
	 * @return {@link HttpErrorInfo}
	 */
	private HttpErrorInfo createHttpError(Exception ex, HttpStatus status) {
		
		HttpErrorInfo out = new HttpErrorInfo();
		out.setHttpStatus(status.name());
		out.setMessage(ex.getMessage());
		out.setTimestamp(OffsetDateTime.now());
		
		return out;
	}
}
