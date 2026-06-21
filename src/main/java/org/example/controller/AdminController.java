package org.example.controller;

import org.example.service.CardService;
import org.example.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static org.example.util.ScannerUtil.getInt;
import static org.example.util.ScannerUtil.getStr;

@Controller
public class AdminController {
    @Autowired
    private CardService cardService;
    @Autowired
    private TerminalService terminalService;

    public void start() {
        while (true) {
            showMenu();
            switch (getInt("choose one")) {
                case 1 -> addCard();
                case 2 -> cardService.cardList();
                case 3 -> updateCard();
                case 4 -> changeCardStatus();
                case 5 -> deleteCard();
                case 6 -> createTerminal();
                case 7 -> terminalService.terminalList();
                case 8 -> updateTerminal();
                case 9 -> changeTerminalStatus();
                case 10 -> deleteTerminal();
                case 11 -> {}
                case 12 -> {}
                case 13 -> {}
                case 14 -> {}
                case 15 -> {}
                case 16 -> {}
                case 17 -> {}
                case 18 -> {}
                case 19 -> {}
                case 0 -> {return;}
                default -> System.out.println("wrong unput. Mazgi");
            }
        }
    }

    private void showMenu() {
        System.out.println("""
                *** Admin Menu ***
                1. Create Card
                2. Card List
                3. Update Card
                4. Change Card Status
                5. Delete Card
                6. Create Terminal
                7. Terminal List
                8. Update Terminal
                9. Change Terminal Status
                10. Delete Terminal
                11. Profile lits
                12. Change Profile Status
                13. Transaction List
                14. Company Card Balance
                15. Bugungi To'lovlar
                16. Kunlik to'lovlar
                17. Oraliq to'lovlar
                18. Transaction by Terminal
                19. Transaction by Car
                0. Exit""");
    }

    private void addCard() {
        String cardNumber = getStr("Enter card number");
        String expiredDate = getStr("Enter card expired dare (MM/yy)");
        cardService.createCard(cardNumber, expiredDate);
    }

    private void updateCard() {
        String cardNumber = getStr("Enter card number");
        String expiredDate = getStr("Enter card expired dare (MM/yy)");
        cardService.adminUpdateCard(cardNumber, expiredDate);
    }

    private void changeCardStatus() {
        String cardNumber = getStr("Enter card number");
        cardService.changeCardStatus(cardNumber);
    }

    private void deleteCard() {
        String cardNumber = getStr("Enter card number");
        cardService.deleteCard(cardNumber);
    }

    private void createTerminal() {
        String code = getStr("Enter code");
        String address = getStr("Enter address");
        terminalService.addTerminal(code, address);
    }

    private void updateTerminal() {
        String code = getStr("Enter code");
        String newAddress = getStr("Enter new address");
        terminalService.updateTerminal(code, newAddress);
    }

    private void changeTerminalStatus() {
        String code = getStr("code");
        terminalService.changeTerminalStatus(code);
    }

    private void deleteTerminal() {
        String code = getStr("code");
        terminalService.deleteTerminal(code);
    }
}
