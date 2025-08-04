package com.amoibeojt.api.controller;

import java.util.List;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amoibeojt.api.dto.ApiResponse;
import com.amoibeojt.api.dto.PagedResponse;
import com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO;
import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.exception.InvalidInputException;
import com.amoibeojt.api.service.partsstock.PartsStockService;
import com.amoibeojt.api.validator.PartsStockSearchValidator;

import lombok.RequiredArgsConstructor;

/**
 * 部品在庫照会 Controller
 * 
 * @author your name
 *
 * @return ページング結果
 */

@RestController
@RequestMapping("/api/parts/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PartsStockController {
	
    private final PartsStockService partsStockService;
    
    private final PartsStockSearchValidator validator;

    //バリデータをバインド
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping
    public ApiResponse<PagedResponse<PartsStockResponseDTO>> search(
        @RequestParam(value="center_id",    required=false) List<Integer> centerId,
        @RequestParam(value="category_id",  required=false) List<Integer> categoryId,
        @RequestParam(value="stock_id",     required=false) List<Integer> stockId,
        @RequestParam(value="name_pattern",required=false) String       namePattern,
        @RequestParam(value="amount_min",  required=false) Integer      amountMin,
        @RequestParam(value="amount_max",  required=false) Integer      amountMax
    ) {
        
    	// リクエストパラメータをまとめた検索条件DTOを作成
    	PartsStockSearchDTO criteria = new PartsStockSearchDTO(centerId,categoryId,stockId,namePattern,amountMin,amountMax);

        // バリデーションチェック
        Errors errors = new BeanPropertyBindingResult(criteria, "criteria");
        validator.validate(criteria, errors);
        if (errors.hasErrors()) {
            // 最初のエラーコードをキーにして例外を投げる
            String code = errors.getAllErrors().get(0).getCode();
            throw new InvalidInputException(code);
        }

        // サービス呼び出し → レスポンス構築
        var list = partsStockService.search(criteria);
        var page = new PagedResponse<>(list, list.size());

        // 取得件数が 0 件の場合は、ダミーメッセージを返却
        if (page.getTotal_count() == 0) {
            var dummy = PartsStockResponseDTO.builder()
                .name("一致するデータがありません。")
                .build();
            page.setItems(List.of(dummy));
        }

        return new ApiResponse<>(
            "success",
            "部品在庫情報を正常に取得しました",
            page
        );
    }
}