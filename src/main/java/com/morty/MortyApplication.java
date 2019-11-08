package com.morty;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.*","org.activiti"})
@EnableAutoConfiguration(exclude={SecurityAutoConfiguration.class})
public class MortyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MortyApplication.class, args);
    }

}
