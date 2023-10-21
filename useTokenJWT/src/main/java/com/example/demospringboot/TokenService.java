package com.example.demospringboot;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    public String getToken(User user) {
        String token="";
        // withAudience()存入需要保存在token的信息，这里我把用户ID存入token中
        // Algorithm.HMAC256():使用HS256生成token,密钥则是用户age，唯一密钥的话可以保存在服务端。
        token= JWT.create().withAudience(String.valueOf(user.getId()))// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(String.valueOf(user.getAge())));// 以 age 作为 token 的密钥
        return token;
    }
}