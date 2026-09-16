package validation;

/**
 * TP1-14: Shared input size/required-field validation.
 *
 * The professor requires every textual input field to be checked for a
 * reasonable maximum size BEFORE additional processing.
 *
 * These limits are proposed values for the team to confirm at the next meeting.
 */
public final class InputValidator {

    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_EMAIL_LENGTH = 254;
    public static final int MAX_PASSWORD_LENGTH = 128;
    public static final int MAX_ONE_TIME_PASSWORD_LENGTH = 128;
    public static final int MAX_INVITATION_CODE_LENGTH = 100;
    public static final int MAX_SEARCH_LENGTH = 100;

    private InputValidator() {
        // Utility class; prevent construction.
    }

    /**
     * Returns true if the input is null, empty, or only whitespace.
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Returns true if input exceeds the supplied maximum length.
     */
    public static boolean isTooLong(String input, int maxLength) {
        return input != null && input.length() > maxLength;
    }

    /**
     * Reusable required + max-length check.
     *
     * @param input value to validate
     * @param fieldName human-readable field name
     * @param maxLength maximum accepted length
     * @return empty string when valid; otherwise an error message
     */
    public static String validateRequiredText(
            String input, String fieldName, int maxLength) {

        if (isEmpty(input)) {
            return fieldName + " is required.";
        }

        if (isTooLong(input, maxLength)) {
            return fieldName + " is too long. Maximum length is "
                    + maxLength + " characters.";
        }

        return "";
    }

    /**
     * Optional-field max-length check. Empty input is accepted.
     */
    public static String validateOptionalText(
            String input, String fieldName, int maxLength) {

        if (input == null || input.isEmpty()) {
            return "";
        }

        if (isTooLong(input, maxLength)) {
            return fieldName + " is too long. Maximum length is "
                    + maxLength + " characters.";
        }

        return "";
    }
}
