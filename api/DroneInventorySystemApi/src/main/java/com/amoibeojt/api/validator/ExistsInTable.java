package com.amoibeojt.api.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 指定されたテーブルにIDが存在するかをチェックするバリデーションアノテーション
 * 
 * @author your name
 * 
 */
@Documented
@Constraint(validatedBy = ExistsInTableValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsInTable {
    
    String message() default "指定されたIDは存在しません";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * チェック対象のエンティティクラス
     */
    Class<?> entity();
    
    /**
     * チェック対象のフィールド名（デフォルトは"id"）
     */
    String field() default "id";
    
    /**
     * nullを許可するか（デフォルトはtrue）
     */
    boolean allowNull() default true;
}
