package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetailDTO {
    private TransactionDTO transaction;
    private TerminalDTO terminal;
    private CardDTO card;
    private ProfileDTO profile;
}
