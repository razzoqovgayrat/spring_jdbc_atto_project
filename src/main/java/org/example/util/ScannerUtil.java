package org.example.util;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class ScannerUtil {

    private static Scanner scannerNum;
    private static Scanner scannerStr;

    public ScannerUtil() {
        scannerNum = new Scanner(System.in);
        scannerStr = new Scanner(System.in);
    }

    public static int getInt(String text) {
        int n = -1;
        try {
            System.out.print(text + ": ");
            n = scannerNum.nextInt();
        } catch (InputMismatchException e) {
            scannerNum = new Scanner(System.in);
        }
        return n;
    }

    public static double getDouble(String text) {
        double n = -1;
        try {
            System.out.print(text + ": ");
            n = scannerNum.nextDouble();
        } catch (InputMismatchException e) {
            scannerNum = new Scanner(System.in);
        }
        return n;
    }

    public static String getStr(String text) {
        System.out.print(text + ": ");
        return scannerStr.nextLine();
    }
}
