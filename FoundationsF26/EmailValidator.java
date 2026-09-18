package validation;

import java.util.regex.Pattern;

/**
 * TP1-15: Reasonable email-address validation for TP1.
 *
 * This is intentionally a practical application-level validator rather than
 * a full RFC email parser.
 */
public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private EmailValidator() {
        // Utility class; prevent construction.
    }

    /**
     * Validates email address format and maximum length.
     *
     * Validation order:
     * 1. Required check
     * 2. Maximum-size check
     * 3. Reasonable format checks
     *
     * @param email email address to validate
     * @return empty string when valid; otherwise an error message
     */
    public static String validate(String email) {

        if (email == null || email.trim().isEmpty()) {
            return "Email address is required.";
        }

        // TP1-14: check size BEFORE regex or additional processing.
        if (email.length() > InputValidator.MAX_EMAIL_LENGTH) {
            return "Email address is too long. Maximum length is "
                    + InputValidator.MAX_EMAIL_LENGTH + " characters.";
        }

        String trimmed = email.trim();

        if (trimmed.contains(" ")) {
            return "Please enter a valid email address.";
        }

        if (trimmed.contains("..")) {
            return "Please enter a valid email address.";
        }

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return "Please enter a valid email address.";
        }

        return "";
    }

    /**
     * Convenience method for boolean-style checks.
     */
    public static boolean isValid(String email) {
        return validate(email).isEmpty();
    }
}
