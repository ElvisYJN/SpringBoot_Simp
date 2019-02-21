package com.xinhuanet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JspController {

    @RequestMapping("/findJsp")
    public ModelAndView findJsp(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", "114955");
        modelAndView.setViewName("/WEB-INF/1.jsp");
        return modelAndView;
    }

}
