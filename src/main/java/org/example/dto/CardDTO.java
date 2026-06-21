package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.GeneralStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CardDTO {
    private int id;
    private String cardNumber;
    private LocalDate expDate;
    private double balance;
    private GeneralStatus status;
    private boolean visible;
    private LocalDateTime createdDate;
}
