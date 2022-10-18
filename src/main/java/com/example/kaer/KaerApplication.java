package com.example.kaer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.example.kaer.mapper")
@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class KaerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KaerApplication.class, args);
    }

}
