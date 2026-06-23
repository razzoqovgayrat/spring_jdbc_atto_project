package org.example.service;

import org.example.container.ComponentContainer;
import org.example.dto.CardDTO;
import org.example.dto.ProfileCardDTO;
import org.example.dto.TerminalDTO;
import org.example.enums.GeneralStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.ProfileCardRepository;
import org.example.repository.TerminalRepository;
import org.example.util.CardUtil;
import org.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileCardRepository profileCardRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ComponentContainer container;

    public void createCard(String cardNumber, String expiredDate) {
        if (!isValidCardNumber(cardNumber)) {
            System.out.println("invalid card number");
            return;
        }

        LocalDate expDate = getCardExpDate(expiredDate);
        if (expDate == null) {
            System.out.println("invalid expired date");
            return;
        }

        CardDTO exist = cardRepository.getCardByNumber(cardNumber);
        if (exist != null) {
            System.out.println("Card number is exist");
            return;
        }

        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setExpDate(expDate);
        cardDTO.setBalance(0d);
        cardDTO.setStatus(GeneralStatus.ACTIVE);
        cardDTO.setCreatedDate(LocalDateTime.now());

        if (cardRepository.save(cardDTO) == 1) {
            System.out.println("card successfully created");
        } else {
            System.out.println("error");
        }
    }

    public void cardList() {
        List<CardDTO> list = cardRepository.getList();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("|                                 Card List                                 |");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-20s | %-12s | %-8s | %-17s |%n", "Id", "CardNumber", "ExpiredDate", "Status", "CreatedDate");
        System.out.println("-----------------------------------------------------------------------------");

        list.forEach(cardDTO -> {
            String cardNumber = CardUtil.replaceWithStar(cardDTO.getCardNumber());
            String expDate = DateUtil.toMonthAndYear(cardDTO.getExpDate());
            String createdDate = DateUtil.toSimpleFormat(cardDTO.getCreatedDate());

            System.out.printf("| %-4d | %-20s | %-12s | %-8s | %-17s |%n",
                    cardDTO.getId(), cardNumber, expDate, cardDTO.getStatus(), createdDate);
        });
        System.out.println("-----------------------------------------------------------------------------");
    }

    public void adminUpdateCard(String cardNumber, String expiredDate) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        LocalDate newExpDate = getCardExpDate(expiredDate);
        if (newExpDate == null) {
            System.out.println("wrong exp date");
            return;
        }

        if (cardRepository.updateCard(cardNumber, newExpDate) == 1) {
            System.out.println("successfully updated");
        } else {
            System.out.println("error");
        }
    }

    private LocalDate getCardExpDate(String expiredDate) {
        try {
            int month = Integer.parseInt(expiredDate.substring(0, 2));
            int year = 2000 + Integer.parseInt(expiredDate.substring(3));
            return LocalDate.of(year, month, 1);
        } catch (RuntimeException e) {
            System.out.println("Card expired date is wrong");
        }
        return null;
    }

    public void changeCardStatus(String cardNumber) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        if (cardByNumber.getStatus().equals(GeneralStatus.ACTIVE)) {
            cardRepository.changeCardStatus(cardNumber, GeneralStatus.BLOCK);
            System.out.println("card blocked");
        } else {
            cardRepository.changeCardStatus(cardNumber, GeneralStatus.ACTIVE);
            System.out.println("card activated");
        }
    }

    public void deleteCard(String cardNumber) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        if (!cardByNumber.isVisible()) {
            System.out.println("card already deleted");
            return;
        }

        if (cardRepository.deleteCard(cardNumber) == 1) {
            System.out.println("card deleted");
        }
    }

    public void addCardToProfile(String cardNumber) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        if (!cardByNumber.getStatus().equals(GeneralStatus.ACTIVE) || !cardByNumber.isVisible()) {
            System.out.println("card is wrong status");
            return;
        }

        ProfileCardDTO exists = profileCardRepository.getProfileCardByCardId(cardByNumber.getId());
        if (exists != null) {
            System.out.println("Card is assigned to another user");
            return;
        }

        ProfileCardDTO profileCardDTO = new ProfileCardDTO();
        profileCardDTO.setCardId(cardByNumber.getId());
        profileCardDTO.setProfileId(container.getCurrentProfile().getId());

        if (profileCardRepository.save(profileCardDTO) == 1) {
            System.out.println("card added to profile");
        } else {
            System.out.println("error");
        }
    }

    public void profileCardList(int profileId) {
        List<ProfileCardDTO> list = profileCardRepository.getProfileCardListByProfileId(profileId);
        if (list.isEmpty()) return;
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("|                              Profile Card List                               |");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-12s | %-8s | %-7s | %-17s |%n","CardNumber", "ExpiredDate", "Balance", "status", "CreatedDate");
        System.out.println("--------------------------------------------------------------------------------");
        list.forEach(profileCardDTO -> {
            String cardNumber = CardUtil.replaceWithStar(profileCardDTO.getCard().getCardNumber());
            String expDate = DateUtil.toMonthAndYear(profileCardDTO.getCard().getExpDate());
            double balance = profileCardDTO.getCard().getBalance();
            String createdDate = DateUtil.toSimpleFormat(profileCardDTO.getCreatedDate());
            String status = profileCardDTO.getCard().getStatus().toString();

            System.out.printf("| %-20s | %-12s | %-8s | %-7s | %-17s |%n", cardNumber, expDate, balance, status, createdDate);
        });
        System.out.println("--------------------------------------------------------------------------------");
    }

    public void profileChangeCardStatus(String cardNumber, int profileId) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        ProfileCardDTO profileCardByCardId = profileCardRepository.getProfileCardByCardId(cardByNumber.getId());
        if (profileCardByCardId == null || profileCardByCardId.getProfileId() != profileId) {
            System.out.println("Mazgi card not belongs to you");
            return;
        }

        if (cardByNumber.getStatus().equals(GeneralStatus.ACTIVE)) {
            cardRepository.changeCardStatus(cardNumber, GeneralStatus.BLOCK);
            System.out.println("card blocked");
        } else {
            cardRepository.changeCardStatus(cardNumber, GeneralStatus.ACTIVE);
            System.out.println("card activated");
        }
    }

    public void profileRefillCard(String cardNumber, double amount) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        if (!cardByNumber.getStatus().equals(GeneralStatus.ACTIVE) || !cardByNumber.isVisible()) {
            System.out.println("card is wrong status");
            return;
        }

        if (cardRepository.cardDebit(cardNumber, amount) == 1) {
            System.out.println("card refilled");
        } else {
            System.out.println("something went wrong");
        }

        transactionService.createTransaction(cardByNumber.getId(), null, amount, TransactionType.REFILL);
    }

    public void cardBalance(String companyCard) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(companyCard);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }
        System.out.println("Card Balance: " + cardByNumber.getBalance());
    }

    private boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank() || cardNumber.length() != 16) {
            return false;
        }
        for (int i = 0; i < cardNumber.length(); i++) {
            if (Character.isDigit(cardNumber.charAt(i)))
                return false;
        }
        return true;
    }

    public void deleteProfileCard(String cardNumber) {
        CardDTO cardByNumber = cardRepository.getCardByNumber(cardNumber);
        if (cardByNumber == null) {
            System.out.println("card not found");
            return;
        }

        if (!cardByNumber.isVisible()) {
            System.out.println("card already deleted");
            return;
        }

        ProfileCardDTO profileCardByCardId = profileCardRepository.getProfileCardByCardId(cardByNumber.getId());
        if (profileCardByCardId == null) {
            System.out.println("profile card not found");
            return;
        }

        if (profileCardRepository.deleteProfileCardById(profileCardByCardId.getId()) == 1) {
            System.out.println("profile card deleted");
        } else {
            System.out.println("error");
        }

    }
}
