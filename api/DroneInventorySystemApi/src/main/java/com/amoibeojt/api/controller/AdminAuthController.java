package com.amoibeojt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amoibeojt.api.dto.ResponseMessageDTO;
import com.amoibeojt.api.entity.AdminInfo;
import com.amoibeojt.api.service.AdminInfoService;
import com.amoibeojt.api.util.JwtUtils;

/**
 * 管理者認証APIコントローラー
 * 
 * @author your name
 *
 */

@RestController
@RequestMapping("/api/admin/auth")
public class AdminAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminInfoService adminInfoService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminInfo adminInfo) {
        try {
            // 認証処理
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(adminInfo.getAdminId(), adminInfo.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT生成
            String token = jwtUtils.generateToken(adminInfo.getAdminId());

            // トークンを返却する
            return ResponseEntity.ok(new ResponseMessageDTO("Login successful", HttpStatus.OK.value(), token));

        } catch (Exception e) {
            // 認証失敗時のレスポンス
        	ResponseMessageDTO responseMessage = new ResponseMessageDTO("Invalid credentials", HttpStatus.UNAUTHORIZED.value(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }
    }
    
    @PostMapping("/register")
    public String register(@RequestBody AdminInfo adminInfo) {
        adminInfoService.save(adminInfo);
        return "Admin registered successfully";
    }
}