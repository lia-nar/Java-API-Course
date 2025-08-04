package com.amoibeojt.api.util;

import java.util.regex.Pattern;

/**
 * 入力値バリデータ
 * SQLインジェクションとXSS対策
 * 
 * @author your name
 *
 */

public class InputValidator {
	// SQLインジェクションパターン
	private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
	    "(['\";\\-\\-]|\\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE)?|INSERT|MERGE|SELECT|UPDATE|UNION|USE)\\b)",
	    Pattern.CASE_INSENSITIVE
	);
    // XSSパターン
    private static final Pattern XSS_PATTERN = Pattern.compile(
        "<script>(.*?)</script>|<.*?javascript:.*?>|<.*?\\s+on.*?>",
        Pattern.CASE_INSENSITIVE
    );
    // 入力値のバリデーシ
    public static boolean isValid(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        return !SQL_INJECTION_PATTERN.matcher(input).find() && !XSS_PATTERN.matcher(input).find();
    }
    // 数値のバリデーショ
    public static boolean isValidNumber(String value) {
        try {
            int number = Integer.parseInt(value);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
