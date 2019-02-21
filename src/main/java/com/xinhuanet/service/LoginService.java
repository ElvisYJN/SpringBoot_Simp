package com.xinhuanet.service;

import com.xinhuanet.entity.Login;
import org.apache.ibatis.annotations.Param;

public interface LoginService {

    Login selectById(@Param(value = "id") Integer id);

}
