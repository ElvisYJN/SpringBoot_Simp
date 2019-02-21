package com.xinhuanet.mapper;


import com.xinhuanet.entity.Login;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper {

    @Select("SELECT id,username,password FROM login WHERE ID = '${id}'")
    Login selectById(@Param(value = "id") Integer id);

}
