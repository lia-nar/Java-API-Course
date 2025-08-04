package com.amoibeojt.api.service.partsstock;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amoibeojt.api.dto.partsstock.PartsStockResponseDTO;
import com.amoibeojt.api.dto.partsstock.PartsStockSearchDTO;
import com.amoibeojt.api.repository.PartsStockRepository;

import lombok.RequiredArgsConstructor;

/**
 * 部品在庫照会のサービスクラス（DTO にマッピング）
 * 
 * @author your name
 */
@Service
@RequiredArgsConstructor
public class  PartsStockService {
	
    private final PartsStockRepository repository;
	
    /**
     * 検索条件に合致する在庫情報を取得
     * @param search 検索条件DTO
     * @return 条件に合致した在庫一覧
     */
	public List<PartsStockResponseDTO> search(PartsStockSearchDTO search) {
	    return repository.searchByCriteriaWithJoin(
	        emptyToNull(search.getCenterId()),
	        emptyToNull(search.getCategoryId()),
	        emptyToNull(search.getStockId()),
	        search.getNamePattern(),
	        search.getAmountMin(),
	        search.getAmountMax()
	    );
	}

	private <T> List<T> emptyToNull(List<T> list) {
	    return (list == null || list.isEmpty()) ? null : list;
	}

}