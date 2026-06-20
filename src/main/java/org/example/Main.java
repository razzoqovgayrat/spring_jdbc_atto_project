package org.example;

import org.example.config.ApplicationConfig;
import org.example.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        MainController mainController = context.getBean("mainController", MainController.class);
        mainController.start();
    }
}