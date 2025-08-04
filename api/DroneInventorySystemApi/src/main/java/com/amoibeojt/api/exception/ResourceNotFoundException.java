package com.amoibeojt.api.exception;

import java.util.ResourceBundle;

/**
 * リソース未存在例外
 * 
 * @author your name
 *
 */

public class ResourceNotFoundException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public ResourceNotFoundException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}
