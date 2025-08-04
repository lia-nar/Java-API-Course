package com.amoibeojt.api.exception;

import java.util.ResourceBundle;

/**
 * 重複リクエスト例外
 * 
 * @author your name
 *
 */

public class DuplicateRequestException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public DuplicateRequestException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}