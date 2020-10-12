package com.cav.clairvoyance.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "视图")
@Controller
public class IndexController {

    @GetMapping(value = {"/","/index","/code"})
    public String codeTest() {
        return "code_test";
    }

    @GetMapping("/batch")
    public String batchTest() {
        return "batch_test";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String error405() {
        return "error/500";
    }


}
