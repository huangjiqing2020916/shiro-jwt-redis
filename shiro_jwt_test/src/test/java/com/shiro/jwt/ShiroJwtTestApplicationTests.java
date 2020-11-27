package com.shiro.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.service.RealmUserService;
import com.shiro.jwt.util.PasswordUtil;
import com.shiro.jwt.util.RedisUtil;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class ShiroJwtTestApplicationTests {

    //签名私钥
    private static final String SING = "shiro-token";
    public static final int MINUTE = 60000;//分钟
    public static final long EXPIRE_TIME = MINUTE * 1;
    //签名的失效时间
    private static long ttl = 36;

    @Autowired
    private RealmUserService realmUserService;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    @Test
    void test() {
        RealmUser realmUser = realmUserService.queryUserByUsername("hjq");
        System.err.println(realmUser);
    }

    @Test
    void createToken() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //创建JwtBuilder
        Date dd = new Date();
        String one = formatter.format(dd);
        System.err.println(one);
        System.err.println(EXPIRE_TIME);
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        String two = formatter.format(date);
        System.err.println(two);
        JwtBuilder jwtBuilder = Jwts.builder().setId("id").setSubject("username")
                .signWith(SignatureAlgorithm.HS256, SING);

        //根据map设置clamis
        Map<String, Object> map = new HashMap<>();
        map.put("username", "123");
        jwtBuilder.setClaims(map);
        //设置失效时间
        jwtBuilder.setExpiration(date);
        String token = jwtBuilder.compact();
        System.err.println(token);
    }

    @Test
    void getToken() {
        String token =
                "";
        System.err.println("检验token");
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SING).parseClaimsJws(token).getBody();
            String username = (String) claims.get("username");
            System.err.println(username);
        } catch (ExpiredJwtException e) {
            System.err.println("======token过期====================================");
        } catch (SignatureException e) {
            System.err.println("======无效的token====================================");
        } catch (MalformedJwtException e) {
            System.err.println("======token格式不正确====================================");
        } catch (IllegalArgumentException e) {
            System.err.println("======token为空====================================");
        }
    }

    @Test
    void cToken() {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256("secret");
        // 附带username信息
        System.err.println(JWT.create().withClaim("username", "username").withExpiresAt(date).sign(algorithm));
    }

    @Test
    void yanzheng(){
        String token =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDYzNzI1MjQsInVzZXJuYW1lIjoiaGpxIn0.ZFft6iRYMhspBt66zHJ3Ugjf4oj23WgeaWpTvOJ9L7U";
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256("34eff40bb161bbaf");
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", "hjq").build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception) {
            System.err.println(exception.toString());
        }finally {

        }
    }
//JWTVerificationException

    @Test
    void getUsername() {
        String token =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDYzNjI2NDMsInVzZXJuYW1lIjoiaGpxIn0.WPulbSfg7ROMORNvK4o3wSc1ZfUDlLzIekR7FmOYJ5Y";
        try {
            DecodedJWT jwt = JWT.decode(token);
            String name = jwt.getClaim("username").asString();
            System.err.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    void passwordjiami() {
        String hjq = PasswordUtil.encrypt("hjq", "123456", "RCGTeGiH");
        String lh = PasswordUtil.encrypt("lh", "123456", "RCGTeGiH");
        String xwf = PasswordUtil.encrypt("xwf", "123456", "RCGTeGiH");
        System.err.println(hjq + ":" + lh + ":" + xwf);

    }


    @Test
    void time (){
        String token =
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDYzNjI2NDMsInVzZXJuYW1lIjoiaGpxIn0.WPulbSfg7ROMORNvK4o3wSc1ZfUDlLzIekR7FmOYJ5Y"
                ;
        System.err.println(JWT.decode(token).getExpiresAt());
        String existToken = String.valueOf(redisUtil.get("PREFIX_USER_TOKEN_" + token));
        System.err.println(existToken);
    }



}

