package org.example.service;

import org.example.controller.AdminController;
import org.example.controller.UserController;
import org.example.dto.ProfileDTO;
import org.example.enums.ProfileRole;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileRepository;
import org.example.util.MD5Util;
import org.example.util.ProfileValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserController userController;

    public void login(String phone, String pswd) {
        ProfileDTO isFound = profileRepository.getByPhone(phone);
        if (isFound == null) {
            System.out.println("phone or password wrong ");
            return;
        }

        if (!isFound.getPswd().equals(MD5Util.getMd5Hash(pswd))) {
            System.out.println("phone or password wrong ");
            return;
        }

        if (!isFound.getStatus().equals(ProfileStatus.ACTIVE)) {
            System.out.println("profile in wrong status");
            return;
        }

        System.out.println("\n----- Welcome to the atto system -----\n");
        if (isFound.getRole().equals(ProfileRole.ADMIN)) {
            adminController.start();
        } else if (isFound.getRole().equals(ProfileRole.USER)) {
            userController.start();
        }

    }

    public void registration(ProfileDTO profile) {
        if (!ProfileValidationUtil.isValid(profile)) {
            return;
        }

        ProfileDTO byPhone = profileRepository.getByPhone(profile.getPhone());
        if (byPhone != null) {
            System.out.println("profile already exists");
            return;
        }

        profile.setCreatedDate(LocalDateTime.now());
        profile.setVisible(true);
        profile.setRole(ProfileRole.USER);
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setPswd(MD5Util.getMd5Hash(profile.getPswd()));
        int effectedRows = profileRepository.create(profile);
        if (effectedRows == 1) {
            System.out.println("Registration completed.");
        } else {
            System.out.println("Registration failed.");
        }
    }
}
