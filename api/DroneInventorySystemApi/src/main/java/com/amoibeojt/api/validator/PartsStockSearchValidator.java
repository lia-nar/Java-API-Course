package com.amoibeojt.api.validator;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.exception.InvalidInputException;

/**
 * 部品在庫照会 バリデーション
 * 
 * @author your name
 *
 * @return バリデーション結果
 */

@Component
public class PartsStockSearchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PartsStockSearchDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	
        PartsStockSearchDTO dto = (PartsStockSearchDTO) target;
        
        // 必須項目チェック
        if (dto.getStockId() == null) {
        	throw new InvalidInputException("stockIdIsRequired");
        }

        // 数量範囲チェック
        if (dto.getAmountMin() != null && dto.getAmountMax() != null
                && dto.getAmountMin() > dto.getAmountMax()) {
        	throw new InvalidInputException("amountMinGtMax");
        }

        // リスト内の空要素チェック
        checkList(dto.getCenterId(),  "centerIds",  errors);
        checkList(dto.getCategoryId(),"categoryIds",errors);
        checkList(dto.getStockId(),   "stockIds",   errors);
    }

    private void checkList(List<Integer> list, String field, Errors errors) {
        if (list != null) {
            for (Integer v : list) {
                if (v == null) {
                	throw new InvalidInputException(field + "InvalidElement");
                }
            }
        }
    }
}
