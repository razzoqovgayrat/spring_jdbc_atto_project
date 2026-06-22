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

        // 998911234567 - admin
        // 998911234568 - user

        // began 9:30; break 1:30; begun 2:10 end 4:40
        // began 10:20; end 12:00; begun 1:37 end 7:04
    }
}