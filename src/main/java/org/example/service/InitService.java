package org.example.service;

import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileRepository;
import org.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InitService {

    @Autowired
    private ProfileRepository profileRepository;

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
}
