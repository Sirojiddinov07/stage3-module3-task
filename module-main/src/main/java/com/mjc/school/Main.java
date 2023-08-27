package com.mjc.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.mjc.school.repository",
        "com.mjc.school.service",
        "com.mjc.school.controller",
        "com.mjc.school"})
public class Main implements CommandLineRunner {

    private Menu menu;

    @Autowired
    public Main(Menu menu) {
        this.menu = menu;
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        menu.printMenu();
    }
}
