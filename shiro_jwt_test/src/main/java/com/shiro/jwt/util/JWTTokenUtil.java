package com.shiro.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangjiqing
 * @date 2020/11/25 11:11
 */
@Slf4j
public class JWTTokenUtil {
    //签名私钥

    public static final int MINUTE = 60000;
    public static final long EXPIRE_TIME = MINUTE * 1;//分钟
    public static final long REDIS_TIME = EXPIRE_TIME / 1000;


    public static String createJwtToken(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 附带username信息
        return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
    }

    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
