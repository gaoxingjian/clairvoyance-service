package com.cav.clairvoyance.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "视图")
@Controller
public class IndexController {

    @GetMapping(value = {"/", "/index", "/code"})
    public String codeTest() {
        return "code_test";
    }

    @GetMapping("/batch")
    public String batchTest() {
        return "batch_test";
    }

    @GetMapping("/guidance")
    public String guidance() {
        return "guidance";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
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
