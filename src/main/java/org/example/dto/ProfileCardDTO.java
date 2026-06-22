package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileCardDTO {
    private int id;
    private int cardId;
    private int profileId;
    private LocalDateTime createdDate;
    private CardDTO card;
}
