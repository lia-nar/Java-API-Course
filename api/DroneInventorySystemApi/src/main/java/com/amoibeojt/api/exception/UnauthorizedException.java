package com.amoibeojt.api.exception;

import java.util.ResourceBundle;

/**
 * 認証エラー例外
 * 
 * @author your name
 *
 */

public class UnauthorizedException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public UnauthorizedException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}