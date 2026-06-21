package org.example.util;

import org.example.dto.ProfileDTO;

public class ProfileValidationUtil {

    public static boolean isValid(ProfileDTO profile) {
        if (profile.getName() == null || profile.getName().isBlank() || profile.getName().length() < 2) {
            System.out.println("name is wrong");
            return false;
        }

        if (profile.getSurname() == null || profile.getSurname().isBlank() || profile.getSurname().length() < 2) {
            System.out.println("surname is wrong");
            return false;
        }

        if (profile.getPswd() == null || profile.getPswd().isBlank() || profile.getPswd().length() < 5) {
            System.out.println("password is wrong");
            return false;
        }

        if (profile.getPhone() == null
                || profile.getPhone().isBlank()
                || profile.getPhone().length() < 12
                || !profile.getPhone().startsWith("998")
                || !isOnlyNumber(profile.getPhone())) {
            System.out.println("phone is wrong");
            return false;
        }

        return true;
    }

    private static boolean isOnlyNumber(String phone) {
        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) return false;
        }
        return true;
    }
}
