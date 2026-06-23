package org.example.service;

import org.example.dto.ProfileDTO;
import org.example.enums.GeneralStatus;
import org.example.enums.ProfileStatus;
import org.example.repository.ProfileRepository;
import org.example.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public void profileList() {
        List<ProfileDTO> profileList = profileRepository.profileList();
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("|                                                Terminal List                                              |");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-16s | %-16s | %-14s | %-9s | %-10s | %-18s |%n", "Id", "Name", "Surname", "Phone", "CardCount", "Status", "CreatedDate");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        profileList.forEach(profileDTO -> {
            System.out.printf("| %-4s | %-16s | %-16s | %-14s | %-9s | %-10s | %-18s |%n",
                    profileDTO.getId(), profileDTO.getName(), profileDTO.getSurname(),
                    profileDTO.getPhone(), profileDTO.getCardCount(), profileDTO.getStatus(), DateUtil.toSimpleFormat(profileDTO.getCreatedDate()));
        });
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }

    public void changeProfileStatus(String phone) {
        ProfileDTO profile = profileRepository.getByPhone(phone);
        if (profile == null) {
            System.out.println("profile not found");
            return;
        }

        int n = 0;
        if (profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            n = profileRepository.changeProfileStatus(profile.getId(), ProfileStatus.BLOCK);
        } else {
            n = profileRepository.changeProfileStatus(profile.getId(), ProfileStatus.ACTIVE);
        }
        if (n == 1) System.out.println("profile status changed");
        else System.out.println("error");
    }
}
