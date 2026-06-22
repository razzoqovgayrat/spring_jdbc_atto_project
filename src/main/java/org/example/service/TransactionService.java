package org.example.service;

import org.example.dto.CardDTO;
import org.example.dto.TerminalDTO;
import org.example.dto.TransactionDTO;
import org.example.dto.TransactionDetailDTO;
import org.example.enums.GeneralStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.example.util.CardUtil;
import org.example.util.DateUtil;
import org.example.util.TotalFaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TerminalRepository terminalRepository;
    @Autowired
    private CardRepository cardRepository;

    public void createTransaction(int cardId, Integer terminalId, double amount, TransactionType transactionType) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCardId(cardId);
        transactionDTO.setAmount(amount);
        transactionDTO.setTerminalId(terminalId);
        transactionDTO.setTransactionType(transactionType);
        transactionRepository.createTransaction(transactionDTO);
    }

    public void makePayment(String cardNumber, String terminalCode) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardNumber == null) {
            System.out.println("card not found");
            return;
        }
        if (!cardByNumber.getStatus().equals(GeneralStatus.ACTIVE) || !cardByNumber.isVisible()) {
            System.out.println("card is wrong status");
            return;
        }
        TerminalDTO terminalByCode = terminalRepository.getTerminalByCode(terminalCode);
        if (terminalByCode == null) {
            System.out.println("card not found");
            return;
        }
        if (!terminalByCode.getStatus().equals(GeneralStatus.ACTIVE) || !terminalByCode.isVisible()) {
            System.out.println("Terminal is wrong status");
            return;
        }
        if (cardByNumber.getBalance() < TotalFaire.faire) {
            System.out.println("balance not enough");
            return;
        }

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setCardId(cardByNumber.getId());
        transactionDTO.setTerminalId(terminalByCode.getId());
        transactionDTO.setAmount(TotalFaire.faire);
        transactionDTO.setTransactionType(TransactionType.PAYMENT);

        cardByNumber.setBalance(cardByNumber.getBalance() - TotalFaire.faire);
        cardRepository.updateCardBalance(cardNumber, cardByNumber.getBalance());

        addBalanceToCompanyCard();

        if (transactionRepository.createTransaction(transactionDTO) == 1) {
            System.out.println("transaction has successfully completed");
        } else {
            System.out.println("transaction has not completed");
        }

    }

    private void addBalanceToCompanyCard() {
        CardDTO cardByNumber = cardRepository.getCardByNumber(CardUtil.companyCard);
        cardByNumber.setBalance(cardByNumber.getBalance() + TotalFaire.faire);
        cardRepository.updateCardBalance(cardByNumber.getCardNumber(), cardByNumber.getBalance());
    }

    public void transactionListByProfileId(int profileId) {
        List<TransactionDetailDTO> transactionList = transactionRepository.getTransactionListByProfileId(profileId);
        if (transactionList.isEmpty()) {
            return;
        }
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.println("|                                          Transaction List                                          |");
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-25s | %-15s | %-18s | %-8s |%n", "CardNumber", "Address", "Amount", "TransactionDate", "Type");
        System.out.println("------------------------------------------------------------------------------------------------------");
        transactionList.forEach(dto -> {
            String cardNumber = CardUtil.replaceWithStar(dto.getCard().getCardNumber());
            String address = dto.getTerminal().getAddress();
            double amount = dto.getTransaction().getAmount();
            String createdDate = DateUtil.toSimpleFormat(dto.getTransaction().getCreatedDate());
            TransactionType transactionType = dto.getTransaction().getTransactionType();
            System.out.printf("| %-20s | %-25s | %-15s | %-18s | %-8s |%n", cardNumber, address, amount, createdDate, transactionType);
        });
        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}
