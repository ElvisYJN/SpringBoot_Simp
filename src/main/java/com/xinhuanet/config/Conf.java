package com.xinhuanet.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class Conf {

    @Value("${redis.host}")
    private String redishost;

    @Value("${redis.port}")
    private Integer redisport;

    @Value("${redis.password}")
    private String redispassword;

    @Value("${redis.timeout}")
    private Integer redistimeout;

}
