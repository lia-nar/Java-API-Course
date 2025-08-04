package com.amoibeojt.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amoibeojt.api.entity.AdminInfo;

/**
 * 管理者情報テーブルリポジトリー
 *
 * @author	your name
 * 
 */
@Repository	
public interface AdminInfoRepository extends JpaRepository<AdminInfo, String> 
{
	Optional<AdminInfo> findByAdminId(String adminId);
}
