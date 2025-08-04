package com.amoibeojt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amoibeojt.api.entity.PartsCategoryInfo;
/**
 *  部品カテゴリ情報テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface PartsCategoryInfoRepository extends JpaRepository<PartsCategoryInfo, Integer> {

}
