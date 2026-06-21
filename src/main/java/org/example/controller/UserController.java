package org.example.controller;

import static org.example.util.ScannerUtil.*;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    public void start() {
        while (true) {
            showMenu();
            switch (getInt("choose one")) {
                case 1 -> {}
                case 2 -> {}
                case 3 -> {}
                case 4 -> {}
                case 5 -> {}
                case 6 -> {}
                case 0 -> {return;}
                default -> System.out.println("wrong input");
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                *** User Menu ***
                1. Add Card
                2. Card List
                3. Card Change Status
                4. Delete Card
                5. ReFill
                6. Transaction
                0. Exit""");
    }
}
