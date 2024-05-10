package com.ennov.ticketapi.utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

@Slf4j
public class MyUtils {

    private static final String URL_REGEX =
            "^(www.)" + "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])" +
                    "([).!';/?:,][[:blank:]])?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);



    public static String upperCaseTrim(String str) {
        return str == null ? null : str.toUpperCase().trim();
    }

    public static String lowerCaseTrim(String str) {
        return str == null ? null : str.toLowerCase().trim();
    }



    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }



    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }



    public static String generatedPassWord() {
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";

        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = (int) (AlphaNumericStr.length() * Math.random());
            sb.append(AlphaNumericStr.charAt(index));
        }
        String pass = sb.toString();

        return pass;
    }

}
