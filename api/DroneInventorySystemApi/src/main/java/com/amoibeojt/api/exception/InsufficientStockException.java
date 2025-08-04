package com.amoibeojt.api.exception;

import java.util.ResourceBundle;
/**
 * 在庫不足例外
 * 
 * @author your name
 *
 */

public class InsufficientStockException extends RuntimeException {
	 private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	    public InsufficientStockException(String messageKey) {
	        super(messages.getString(messageKey));
	    }
}