package org.example.controller;

import static org.example.util.ScannerUtil.*;

import org.example.container.ComponentContainer;
import org.example.service.CardService;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ComponentContainer container;
    @Autowired
    private TransactionService transactionService;

    public void start() {
        while (true) {
            showMenu();
            switch (getInt("choose one")) {
                case 1 -> addCard();
                case 2 -> cardList();
                case 3 -> changeCardStatus();
                case 4 -> deleteCard();
                case 5 -> refill();
                case 6 -> transactionList();
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
                6. Transaction List
                0. Exit""");
    }

    private void addCard() {
        String cardNumber = getStr("Enter card number");
        cardService.addCardToProfile(cardNumber);
    }

    private void cardList() {
        cardService.profileCardList(container.getCurrentProfile().getId());
    }

    private void changeCardStatus() {
        String cardNumber = getStr("Enter card number");
        int profileId = container.getCurrentProfile().getId();
        cardService.profileChangeCardStatus(cardNumber, profileId);
    }

    private void deleteCard() {
        String cardNumber = getStr("Enter card number");
        cardService.deleteProfileCard(cardNumber);
    }

    private void refill() {
        String cardNumber = getStr("Enter card number");
        double amount = getDouble("Enter amount");
        cardService.profileRefillCard(cardNumber, amount);
    }

    private void transactionList() {
        transactionService.transactionListByProfileId(container.getCurrentProfile().getId());
    }
}
