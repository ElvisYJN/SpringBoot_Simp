package com.xinhuanet.service.impl;

import com.xinhuanet.entity.Login;
import com.xinhuanet.mapper.LoginMapper;
import com.xinhuanet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public Login selectById(Integer id) {
        return loginMapper.selectById(id);
    }
}
