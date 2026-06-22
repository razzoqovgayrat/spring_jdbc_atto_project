package org.example.util;

public class CardUtil {
    public static final String companyCard = "1234567891234567";
    public static String replaceWithStar(String input) {
        return input.substring(0, 4) + " **** **** " + input.substring(12);
    }
}
