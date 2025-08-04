package com.amoibeojt.api.util;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amoibeojt.api.dto.ResponseMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class); // ロガーの設定

    private final JwtUtils jwtUtils;
    
    // application.propertiesからシークレットキーを取得
    @Value("${jwt.jwtSecret}") 
    private String secretKey;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        
        // ログインエンドポイントは認証しない
        if (request.getRequestURI().startsWith("/api/admin/auth/login")) {
            filterChain.doFilter(request, response);  // フィルターをスキップ
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (jwtUtils.validateToken(token)) {
                    String username = Jwts.parserBuilder()
                            .setSigningKey(secretKey)  // シークレットキーを使って検証
                            .build()
                            .parseClaimsJws(token)
                            .getBody()
                            .getSubject();

                    // ユーザーの認証情報を設定
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    // トークンが無効な場合、ログを出力
                    logger.error("Invalid JWT Token");
                    sendErrorResponse(response, "Invalid or expired JWT Token", HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            } catch (ExpiredJwtException e) {
                // JWTの有効期限が切れている場合
                logger.error("JWT Token has expired: " + e.getMessage());
                sendErrorResponse(response, "JWT Token has expired", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception e) {
                // JWTトークンのパースに失敗した場合、エラーログを出力
                logger.error("JWT Token parsing failed: " + e.getMessage(), e);
                sendErrorResponse(response, "JWT Token is invalid or expired", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            // Authorizationヘッダが無い場合のエラー処理
            logger.error("Authorization header is missing or incorrect");
            sendErrorResponse(response, "Authorization header is missing or incorrect", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        // Error responseを作成
        ResponseMessageDTO responseMessage = new ResponseMessageDTO(message, statusCode, null);
        
        // JSONに変換してレスポンスとして返す
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(responseMessage));
    }
}