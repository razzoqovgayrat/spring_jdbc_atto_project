package org.example.service;

import org.example.dto.CardDTO;
import org.example.dto.ProfileDTO;
import org.example.enums.GeneralStatus;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.CardRepository;
import org.example.repository.ProfileRepository;
import org.example.util.CardUtil;
import org.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class InitService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CardRepository cardRepository;

    public void initAdmin() {
        String phone = "998911234567";
        ProfileDTO existsAdmin = profileRepository.getByPhone(phone);
        if (existsAdmin != null) {
            return;
        }
        ProfileDTO profile = new ProfileDTO();
        profile.setName("Alish");
        profile.setSurname("Aliyev");
        profile.setPhone(phone);
        profile.setPswd(MD5Util.getMd5Hash("12345"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setRole(ProfileRole.ADMIN);
        profileRepository.create(profile);
    }

    public void initUser() {
        String phone = "998911234568";
        ProfileDTO existsUser = profileRepository.getByPhone(phone);
        if (existsUser != null) {
            return;
        }
        ProfileDTO profile = new ProfileDTO();
        profile.setName("StudentJon");
        profile.setSurname("Studentov");
        profile.setPhone(phone);
        profile.setPswd(MD5Util.getMd5Hash("12345"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setRole(ProfileRole.USER);
        profileRepository.create(profile);
    }

    public void initCompanyCard() {
        CardDTO cardByNumber = cardRepository.getCardByNumber(CardUtil.companyCard);
        if (cardByNumber != null) {
            return;
        }

        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(CardUtil.companyCard);
        cardDTO.setStatus(GeneralStatus.ACTIVE);
        cardDTO.setBalance(0);
        cardDTO.setExpDate(LocalDate.of(2029, 4, 1));

        cardRepository.save(cardDTO);
    }
}
