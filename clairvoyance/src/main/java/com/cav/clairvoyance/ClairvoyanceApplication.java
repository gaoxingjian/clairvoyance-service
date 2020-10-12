package com.cav.clairvoyance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.cav.clairvoyance.mapper")
public class ClairvoyanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClairvoyanceApplication.class, args);
    }

}
