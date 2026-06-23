package org.example.controller;

import org.example.service.CardService;
import org.example.service.ProfileService;
import org.example.service.TerminalService;
import org.example.service.TransactionService;
import org.example.util.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

import static org.example.util.ScannerUtil.getInt;
import static org.example.util.ScannerUtil.getStr;

@Controller
public class AdminController {
    @Autowired
    private CardService cardService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private TransactionService transactionService;

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
                case 11 -> profileService.profileList();
                case 12 -> changeProfileStatus();
                case 13 -> transactionService.adminTransactionList();
                case 14 -> cardService.cardBalance(CardUtil.companyCard);
                case 15 -> transactionService.paymentByDate(LocalDate.now());
                case 16 -> transactionByDay();
                case 17 -> transactionBetweenDays();
                case 18 -> transactionListByTerminal();
                case 19 -> transactionListByCard();
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
                19. Transaction by Card
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

    private void changeProfileStatus() {
        String phone = getStr("Enter profile phone");
        profileService.changeProfileStatus(phone);
    }

    private void transactionByDay() {
        String date = getStr("Enter (yyyy-MM-dd) date");
        transactionService.paymentByDate(LocalDate.parse(date));
    }

    private void transactionBetweenDays() {
        String fromDate = getStr("Enter (yyyy-MM-dd) from date");
        String toDate = getStr("Enter (yyyy-MM-dd) to date");
        transactionService.transactionBetweenDays(fromDate, toDate);
    }

    private void transactionListByTerminal() {
        String code = getStr("Enter terminal code");
        transactionService.transactionByTerminalCode(code);
    }

    private void transactionListByCard() {
        String cardNumber = getStr("Enter card number");
        transactionService.transactionListByCard(cardNumber);
    }
}
