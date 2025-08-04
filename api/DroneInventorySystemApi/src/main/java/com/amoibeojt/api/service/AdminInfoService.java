package com.amoibeojt.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amoibeojt.api.entity.AdminInfo;
import com.amoibeojt.api.repository.AdminInfoRepository;

/**
 * 管理者情報のサービスクラス
 *
 * @author yure name
 */

@Service
public class AdminInfoService {
    // コンストラクタインジェクション
	@Autowired
    private AdminInfoRepository adminInfoRepository;
    // パスワードエンコーダー
    @Autowired
    private PasswordEncoder passwordEncoder;
    // 管理者情報を取得
    public AdminInfo save(AdminInfo adminInfo) {
        adminInfo.setPassword(passwordEncoder.encode(adminInfo.getPassword()));
        return adminInfoRepository.save(adminInfo);
    }
    // 管理者
    public Optional<AdminInfo> findByAdminId(String adminId) {
        return adminInfoRepository.findByAdminId(adminId);
    }
}
