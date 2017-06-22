package com.database.ProjectDB.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by seba on 2017-05-25.
 */
public class Validation {
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        if (m.matches()) return true;
        else throw new IllegalStateException("Email is incorrect");
    }

    public static boolean isValidPhoneNumber(Long phoneNumber) {
        if (phoneNumber.toString().length() == 9)
            return true;
        else
            throw new IllegalStateException("Number is incorrect");
    }

    public static boolean isValidPesel(String pesel){
        String ePattern = "[0-9]{2}[0-1]{1}[0-9]{1}[0-3]{1}[0-9]{1}\\d{5}";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(pesel);
        if (m.matches()) return true;
        else throw new IllegalStateException("Pesel is incorrect");
    }

    public static boolean isPinCodeCorrect(String pinCode){
        String ePattern = "\\d{2}-\\d{3}";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(pinCode);
        if (m.matches()) return true;
        else throw new IllegalStateException("Pin Code is incorrect");
    }

    public static boolean isHomeNumberCorrect(String homeNumber){
        String ePattern = "[1-9]{1}[\\d{1,}]?\\s?\\w?";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(homeNumber);
        if (m.matches()) return true;
        else throw new IllegalStateException("Home Number is incorrect");
    }

//    public static boolean isStreetCorrent(String street){
//        String ePattern = "[A-Z]{1}[a-z]{1,}\\s?[a-z]{1,}?\\s?[0-9]?/?[0-9]?";
//        Pattern p = Pattern.compile(ePattern);
//        Matcher m = p.matcher(street);
//        if (m.matches()) return true;
//        else return false;
//    }
}
