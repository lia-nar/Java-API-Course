package com.amoibeojt.api.exception;

import java.util.ResourceBundle;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 入力値不正例外
 * 
 * @author your name
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public InvalidInputException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}
