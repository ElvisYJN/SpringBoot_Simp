package com.xinhuanet.controller;

import com.xinhuanet.entity.Login;
import com.xinhuanet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "findById")
    public Login selectById(Integer id) {
        return loginService.selectById(id);
    }

}
