package com.tvd.petcare.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9.]+@(gmail\\.com|lm\\.utc\\.edu\\.vn)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{6,}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return phoneNumber != null && pattern.matcher(phoneNumber).matches();
    }
}
