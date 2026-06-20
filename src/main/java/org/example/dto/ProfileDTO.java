package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private String pswd;
    private LocalDateTime createdDate;
    private boolean visible;
    private ProfileStatus status;
    private ProfileRole role;
}
