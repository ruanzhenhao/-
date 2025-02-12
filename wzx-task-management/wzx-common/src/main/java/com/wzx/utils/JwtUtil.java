package com.wzx.utils;

import com.wzx.properties.JwtProperties;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;

    @Autowired
    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 生成JWT
     *
     * @param claims 设置的信息
     * @return 生成的JWT
     */
    public String createJWT(Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long expMillis = System.currentTimeMillis() + jwtProperties.getAdminTtl();
        Date exp = new Date(expMillis);

        // 打印生成的过期时间，用于调试
        log.info("Token expiration time: {}", exp);

        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(exp)
                .signWith(signatureAlgorithm, jwtProperties.getAdminSecretKey().getBytes(StandardCharsets.UTF_8))
                .compact();

        // 打印生成的Token
        log.info("Generated Token: {}", token);
        return token;
    }

    /**
     * 解析Token
     *
     * @param token 加密后的token
     * @return Claims
     */
    public Claims parseJWT(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getAdminSecretKey().getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("Token已过期: {}", e.getMessage());
            throw new IllegalArgumentException("无效的Token: Token已过期");
        } catch (SignatureException e) {
            log.error("Token签名无效: {}", e.getMessage());
            throw new IllegalArgumentException("无效的Token: 签名无效");
        } catch (Exception e) {
            log.error("Token解析失败: {}", e.getMessage());
            throw new IllegalArgumentException("无效的Token");
        }
    }

    /**
     * 从请求中获取用户ID
     *
     * @param request HTTP请求
     * @return 用户ID
     */
    public Long getUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        log.info("Received token: {}", token);

        if (token != null) {

            try {
                Claims claims = parseJWT(token);
                Long userId = claims.get("userId", Long.class); // 确保使用的键与生成时一致
                log.info("Parsed userId: {}", userId);
                return userId;
            } catch (IllegalArgumentException e) {
                log.error("Token解析失败: {}", e.getMessage());
                throw e; // 重新抛出异常
            }
        }

        log.info("无效的令牌");
        throw new IllegalArgumentException("无效或缺失的令牌");
    }
}