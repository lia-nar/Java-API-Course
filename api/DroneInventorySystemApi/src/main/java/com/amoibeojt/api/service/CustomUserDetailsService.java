package com.amoibeojt.api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amoibeojt.api.entity.AdminInfo;
import com.amoibeojt.api.repository.AdminInfoRepository;

/**
 * カスタムユーザー詳細サービス
 * 
 * @author your name
 *
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {
	// 管理者情報リポジトリーをインジェクション
    @Autowired
    private AdminInfoRepository adminInfoRepository;
    // ユーザ
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminInfo adminInfo = adminInfoRepository.findByAdminId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(adminInfo.getAdminId(), adminInfo.getPassword(), new ArrayList<>());
    }
}