package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.GeneralStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class TerminalDTO {
    private int id;
    private String code;
    private String address;
    private GeneralStatus status;
    private LocalDateTime createdDate;
    private boolean visible;
}
