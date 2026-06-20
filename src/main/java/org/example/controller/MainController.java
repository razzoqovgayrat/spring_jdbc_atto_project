package org.example.controller;

import static org.example.util.ScannerUtil.*;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    public void start() {
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
