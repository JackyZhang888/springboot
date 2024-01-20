package com.example.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.bean.User;
import com.example.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.secret-key}")
    String secretKey;

    @Override
    public String getToken(User user) {
        String token="";
        // withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
        // Algorithm.HMAC256():使用HS256加密生成token,唯一密钥保存在服务端。
        token= JWT.create().withAudience(String.valueOf(user.getUsername()))// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(secretKey));
        return token;
    }

    @Override
    public Boolean parseToken(String token) {
        if (token == null) {
            throw new RuntimeException("缺少token!");
        }
        // 获取 token 中的 user id
        String userName;
        try {
            userName = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(String.valueOf(secretKey))).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }
        log.info("parseToken valid, userName:{}", userName);
        return true;
    }
}
