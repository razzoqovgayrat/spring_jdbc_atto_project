package org.example.controller;

import org.example.dto.ProfileDTO;
import org.example.repository.TableRepository;
import org.example.service.AuthService;
import org.example.service.CardService;
import org.example.service.InitService;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static org.example.util.ScannerUtil.getInt;
import static org.example.util.ScannerUtil.getStr;

@Controller
public class MainController {

    @Autowired
    private InitService initService;
    @Autowired
    private AuthService authService;
    @Autowired
    TableRepository tableRepository;
    @Autowired
    private TransactionService transactionService;

    public void start() {
        tableRepository.createTables();
        initService.initAdmin();
        initService.initUser();
        initService.initCompanyCard();

        while (true) {
            System.out.println("""
                    1. Login
                    2. Registration
                    3. Make Payment
                    0. exit
                    """);
            switch (getInt("choose one")) {
                case 1 -> login();
                case 2 -> registration();
                case 3 -> payment();
                case 0 -> {return;}
                default -> System.out.println("wrong input");
            }
        }
    }

    public void login() {
        String phone = getStr("Enter phone");
        String pswd = getStr("Enter pswd");
        authService.login(phone, pswd);
    }

    public void registration() {
        String name = getStr("Enter name");
        String surname = getStr("Enter surname");
        String phone = getStr("Enter phone");
        String pswd = getStr("Create password");

        ProfileDTO profile = new ProfileDTO();
        profile.setName(name);
        profile.setSurname(surname);
        profile.setPhone(phone);
        profile.setPswd(pswd);

        authService.registration(profile);
    }

    private void payment() {
        String cardNumber = getStr("Enter card number");
        String terminalCode = getStr("Enter terminal code");
        transactionService.makePayment(cardNumber, terminalCode);
    }
}
