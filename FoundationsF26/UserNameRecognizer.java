package validation;

/**
 * TP1-12: Username validation helper.
 *
 * Team rules implemented here:
 * - Username is required.
 * - The first character must be alphabetic.
 * - Remaining non-separator characters may be letters or digits.
 * - Allowed separators are: _, -, ., &
 * - A separator may only appear between two alphanumeric characters.
 * - Consecutive separators are not allowed.
 *
 * IMPORTANT:
 * If the team selects a different HW1 Username Recognizer at the next meeting,
 * replace this implementation with the team's selected recognizer while keeping
 * the same public validate(...) method if possible.
 */
public final class UserNameRecognizer {

    private UserNameRecognizer() {
        // Utility class; prevent construction.
    }

    /**
     * Validates a username.
     *
     * @param username username to validate
     * @return empty string when valid; otherwise a useful error message
     */
    public static String validate(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required.";
        }

        // TP1-14 requirement: size check BEFORE further processing.
        if (username.length() > InputValidator.MAX_USERNAME_LENGTH) {
            return "Username is too long. Maximum length is "
                    + InputValidator.MAX_USERNAME_LENGTH + " characters.";
        }

        if (!Character.isLetter(username.charAt(0))) {
            return "Username must start with an alphabetic character.";
        }

        for (int i = 0; i < username.length(); i++) {
            char current = username.charAt(i);

            if (Character.isLetterOrDigit(current)) {
                continue;
            }

            if (isSeparator(current)) {
                // Separator cannot be first or last.
                if (i == 0 || i == username.length() - 1) {
                    return "A username separator must be between two letters or digits.";
                }

                char previous = username.charAt(i - 1);
                char next = username.charAt(i + 1);

                // Separator must be surrounded by alphanumeric characters.
                if (!Character.isLetterOrDigit(previous)
                        || !Character.isLetterOrDigit(next)) {
                    return "A username separator must be between two letters or digits.";
                }

                continue;
            }

            return "Username contains an invalid character: '" + current + "'.";
        }

        return "";
    }

    /**
     * Convenience method for boolean-style checks.
     *
     * @param username username to validate
     * @return true when username is valid
     */
    public static boolean isValid(String username) {
        return validate(username).isEmpty();
    }

    private static boolean isSeparator(char c) {
        return c == '_' || c == '-' || c == '.' || c == '&';
    }
}
