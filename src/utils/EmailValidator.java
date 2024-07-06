package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {

    // Define a regex pattern for a valid email address
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    // Method to validate email using the pattern
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
