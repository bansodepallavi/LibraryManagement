package com.example.demojwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//import com.example.demojwt.repository.UserRepo;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
/*import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;*/

@Configuration
//@ComponentScan("com.example.demojwt.repository")
//@EntityScan("com.example.demojwt.entity")
//@EnableJpaRepositories("com.example.demojwt.repository")
@SpringBootApplication(scanBasePackages = {"com.example.demojwt"})
public class DemoJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJwtApplication.class, args);
    }

}
