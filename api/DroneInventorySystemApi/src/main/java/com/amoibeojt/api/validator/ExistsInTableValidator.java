package com.amoibeojt.api.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

/**
 * @ExistsInTable IDチェックバリデーターの実装
 * 
 * @author your name
 * 
 */
@Component
public class ExistsInTableValidator implements ConstraintValidator<ExistsInTable, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;
    private String fieldName;

    @Override
    public void initialize(ExistsInTable constraintAnnotation) {
        this.entityClass = constraintAnnotation.entity();
        this.fieldName = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            // エンティティのテーブル名を取得
            String tableName = getTableName(entityClass);
            
            // 存在チェックのクエリを実行
            String sql = String.format("SELECT COUNT(*) FROM %s WHERE %s = :value AND delete_flag = false", tableName, fieldName);
            
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("value", value);
            
            Number count = (Number) query.getSingleResult();
            return count.intValue() > 0;
            
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * エンティティクラスからテーブル名を取得
     */
    private String getTableName(Class<?> entityClass) {
        jakarta.persistence.Table tableAnnotation = entityClass.getAnnotation(jakarta.persistence.Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        
        // @Tableアノテーションがない場合はクラス名をスネークケースに変換
        return camelToSnake(entityClass.getSimpleName());
    }

    /**
     * キャメルケースをスネークケースに変換
     */
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
