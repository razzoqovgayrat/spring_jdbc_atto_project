package org.example.controller;

import static org.example.util.ScannerUtil.*;

import org.example.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {

    @Autowired
    private InitService initService;

    public void start() {
        initService.initAdmin();

        while (true) {
            System.out.println("""
                    1. Login
                    2. Registration
                    3. Make Payment
                    0. exit
                    """);
            switch (getInt("choose one")) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {}
                case 0 -> {return;}
                default -> System.out.println("wrong input");
            }
        }
    }
}
