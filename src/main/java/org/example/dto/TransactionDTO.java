package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.TransactionType;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private int id;
    private int cardId;
    private double amount;
    private Integer terminalId;
    private TransactionType transactionType;
    private LocalDateTime createdDate;
}
