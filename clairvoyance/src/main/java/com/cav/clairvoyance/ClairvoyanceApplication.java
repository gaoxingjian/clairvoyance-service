package com.cav.clairvoyance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class ClairvoyanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClairvoyanceApplication.class, args);
        System.out.println("启动成功");
    }

}
