package com.amoibeojt.api.exception;

import java.util.ResourceBundle;

/**
 * 権限不足例外
 * 
 * @author your name
 *
 */

public class ForbiddenException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public ForbiddenException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}
