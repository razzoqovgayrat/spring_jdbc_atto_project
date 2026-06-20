package org.example;

import org.example.config.ApplicationConfig;
import org.example.controller.MainController;
import org.example.repository.TableRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        TableRepository tableRepository = context.getBean("tableRepository", TableRepository.class);
        tableRepository.createTables();

        MainController mainController = context.getBean("mainController", MainController.class);
        mainController.start();


    }
}