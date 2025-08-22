package com.amoibeojt.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amoibeojt.api.dto.ApiResponse;
import com.amoibeojt.api.dto.PagedResponse;
import com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO;
import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.dto.partsstock.ReceiveItemDTO;
import com.amoibeojt.api.dto.partsstock.ReceiveRequestDTO;
import com.amoibeojt.api.exception.InvalidInputException;
import com.amoibeojt.api.service.partsstock.PartsStockReceiveService;
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
    
    private final PartsStockReceiveService partsStockReceiveService;
    
    private final PartsStockSearchValidator validator;

    //バリデータをバインド - 特定のパラメータ名にのみ適用
    @InitBinder("criteria")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    /**
     * 部品在庫照会
     * 
     * @param center_id
     * @param category_id
     * @param stock_id
     * @param name_pattern
     * @param amount_min
     * @param amount_max
     * @return ApiResponse
     * 
     */
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
    
    /**
     * 部品入荷
     * 
     * @param request
     * @return ApiResponse
     * 
     */
    @PostMapping("/receive")
    public ApiResponse<String> registerOrUpdate(@RequestBody @Valid ReceiveRequestDTO request) {
    	
        List<ReceiveItemDTO> items = request.getItems();

        for (ReceiveItemDTO item : items) {
        	
            // 更新前の在庫数量を事前に取得（履歴登録用）
            Integer beforeAmount = partsStockReceiveService.findExistingStockAmount(item.getStock_id());

            // 部品在庫テーブルの更新または新規登録
            Integer actualStockId = partsStockReceiveService.saveOrUpdatePartsStock(item);

            // 部品在庫履歴テーブルの新規登録
            partsStockReceiveService.insertPartsStockHistory(actualStockId,beforeAmount ,item ,request);
            
        }

        return new ApiResponse<>("success", "部品入荷情報を正常に登録しました", null);
    }
}