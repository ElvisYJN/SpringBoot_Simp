package com.xinhuanet.text;

import com.xinhuanet.entity.Login;
import com.xinhuanet.redis.RedisRepository;
import com.xinhuanet.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JunitText {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void test1() {
        Login login = loginService.selectById(1);
        System.out.println(login);
        String s = redisRepository.get("123");
        System.out.println(s);
    }
}
