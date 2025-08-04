package com.amoibeojt.api.util;


import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    // シークレットキー（秘密鍵）
    // application.propertiesからシークレットキーを取得
    @Value("${jwt.jwtSecret}") 
    private static String secretKey;
    
    @Autowired
    public void setEnvironment(Environment environment) {
        secretKey = environment.getProperty("jwt.jwtSecret", "デフォルトのシークレットキー");
    }
    
    // JWTの生成
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 有効期限（例：1時間）
//                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // 有効期限（例：エラー確認用）
                .signWith(getSigningKey()) // HS256アルゴリズムで署名
                .compact();
    }

    // JWTの検証
    public static boolean validateToken(String token) {
        try {
            // トークンをパースして署名の検証を行う
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;  // 検証成功
        } catch (Exception e) {
            return false;  // 検証失敗
        }
    }
    
    // secretKeyをKeyに変換する
    private static Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey); // Base64デコード
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName()); // HS256アルゴリズム用のSecretKeySpec
    }
}