package com.katana.tenement.web;

import com.katana.tenement.framework.util.SnowflakeIdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration
@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.katana"})
@EntityScan("com.katana")
@EnableJpaRepositories("com.katana")
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

    @Bean
    public SnowflakeIdWorker getSnowflake() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        return idWorker;
    }

}

