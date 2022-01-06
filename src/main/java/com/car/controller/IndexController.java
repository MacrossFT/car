package com.car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping("index")
    public String greeting2(HttpServletRequest servletRequest, ModelMap modelMap) {
        return "redirect:/index.html";
    }

}
