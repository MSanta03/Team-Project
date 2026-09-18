package validation;

import emailAddressTestbed.EmailAddressRecognizer;

/**
 * TP1-15: Email-address validation integrated with the professor's FSM.
 * TP1-14 maximum-size validation is performed before the FSM is used.
 */
public final class EmailValidator {

    private EmailValidator() {
        // Utility class; prevent construction.
    }

    /**
     * @param email email address to validate
     * @return empty string when valid; otherwise a helpful error message
     */
    public static String validate(String email) {
        String sizeResult = InputValidator.validateRequiredText(
                email, "Email address", InputValidator.MAX_EMAIL_LENGTH);

        if (!sizeResult.isEmpty()) {
            return sizeResult;
        }

        // Use the professor-provided FSM as the actual email format recognizer.
        return EmailAddressRecognizer.checkEmailAddress(email);
    }

    public static boolean isValid(String email) {
        return validate(email).isEmpty();
    }
}
