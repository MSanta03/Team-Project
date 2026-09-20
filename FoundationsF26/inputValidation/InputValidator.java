package inputValidation;

/*******
 * <p> Title: InputValidator Class. </p>
 *
 * <p> Description: Provides reusable validation methods for the free-form text fields used by
 * the TP1 interfaces.  The validation is performed before a value is sent to the database so an
 * invalid or oversized value is never used by the application.</p>
 *
 * <p> The database currently stores these text values in fields with a maximum size of 255
 * characters.  Email addresses also receive a basic syntax check after surrounding whitespace
 * has been removed.</p>
 *
 * @author Tam Bui
 * @version 1.00 2026-09-18 Initial TP1 validation integration
 */
public final class InputValidator {

    // Maximum size used by the current database text fields.
    public static final int MAX_INPUT_LENGTH = 255;

    // Utility class: no object needs to be created.
    private InputValidator() {
    }

    /**
     * Checks only the defensive maximum-size rule.  This is used for optional personal-name
     * fields because names should not be restricted to letters only.
     *
     * @param input the value entered by the user
     * @param fieldName name of the field used in the error message
     * @return an empty String when valid; otherwise a useful error message
     */
    public static String validateMaximumLength(String input, String fieldName) {
        if (input == null) {
            return "";
        }
        if (input.length() > MAX_INPUT_LENGTH) {
            return fieldName + " must be " + MAX_INPUT_LENGTH + " characters or fewer.";
        }
        return "";
    }

    /**
     * Validates an email address before it is stored or used to create an invitation.
     * The method checks length first, then required input, then basic email syntax.
     *
     * @param emailAddress the email address entered by the user
     * @return an empty String when valid; otherwise a useful error message
     */
    public static String validateEmailAddress(String emailAddress) {
        if (emailAddress == null) {
            return "Enter a valid email address.";
        }

        String lengthError = validateMaximumLength(emailAddress, "Email address");
        if (!lengthError.isEmpty()) {
            return lengthError;
        }

        String email = emailAddress.trim();
        if (email.isEmpty()) {
            return "Enter a valid email address.";
        }

        // Basic format: local-part@domain.extension.  This is intentionally a practical GUI
        // check rather than an attempt to implement the entire Internet email specification.
        String emailPattern = "^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@"
                + "[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?"
                + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+$";

        if (!email.matches(emailPattern)) {
            return "Enter a valid email address.";
        }

        return "";
    }
}
