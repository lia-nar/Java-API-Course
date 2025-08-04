package com.amoibeojt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amoibeojt.api.entity.CenterInfo;
/**
 *  センター情報テーブルリポジトリー
 *
 * @author	your name
 * 
 */
public interface CenterInfoRepository extends JpaRepository<CenterInfo, Integer> {

}
