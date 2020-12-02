package com.shiro.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class ShiroJwtTestApplication {

    @Autowired
    private static Environment env;

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(ShiroJwtTestApplication.class, args);
        InetAddress ip4 = Inet4Address.getLocalHost();
        log.warn("Shiro+JWT+Redis核心架构:\t" + ip4.getHostAddress() + "/" + "8080/" + "sys/login");
        log.warn("{" +
                "\"username\":\"\"," +
                "\"password\":\"\"" +
                "}");
    }
}
