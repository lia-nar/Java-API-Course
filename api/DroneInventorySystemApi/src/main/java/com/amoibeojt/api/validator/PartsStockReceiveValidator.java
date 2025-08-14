package com.amoibeojt.api.validator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.amoibeojt.api.dto.partsstock.ReceiveItemDTO;
import com.amoibeojt.api.dto.partsstock.ReceiveRequestDTO;
import com.amoibeojt.api.exception.InvalidInputException;

/**
 * 部品入荷 バリデーション
 * 
 * @author your name
 *
 * @return バリデーション結果
 */

@Component
public class PartsStockReceiveValidator implements Validator {
	
    @Override
    public boolean supports(Class<?> clazz) {
        return ReceiveRequestDTO.class.isAssignableFrom(clazz);
    }

	@Override
	public void validate(Object target, Errors errors) {
		
		ReceiveRequestDTO dto = (ReceiveRequestDTO) target;
        
        // 入荷部品リスト（items）配列 の空チェック
        List<ReceiveItemDTO> items = dto.getItems();
        
        if (items.isEmpty() || items == null){
        	throw new InvalidInputException("requiredIsEmpty");
        }
	}
}
