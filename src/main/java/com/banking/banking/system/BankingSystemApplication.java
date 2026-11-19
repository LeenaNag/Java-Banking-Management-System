
package com.banking.banking.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.banking")
@EntityScan(basePackages = "com.banking")
@EnableJpaRepositories(basePackages = "com.banking")
public class BankingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingSystemApplication.class, args);
    }
}
