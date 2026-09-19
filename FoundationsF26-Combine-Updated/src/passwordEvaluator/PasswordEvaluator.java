package passwordEvaluator;

/*******
 * <p> Title: PasswordEvaluator Class. </p>
 * 
 * <p> Description: This class evaluates a password to determine whether it satisfies the
 * password requirements established by the PasswordEvaluationTestbed-F26 application.
 * 
 * A valid password must contain at least one upper case letter, one lower case letter,
 * one numeric digit, one special character, and at least eight characters.
 * 
 * This class contains only the password evaluation logic so it can be used by multiple
 * application pages without depending on a specific JavaFX View.</p>
 * 
 * @author Team
 * 
 * @version 1.00	2026-09-13 Initial version derived from PasswordEvaluationTestbed-F26
 *  
 */

public class PasswordEvaluator {

	/*-********************************************************************************************

	Attributes used by the password evaluator

	 *********************************************************************************************/

	public static String passwordErrorMessage = "";
	public static String passwordInput = "";
	public static int passwordIndexofError = -1;

	public static boolean foundUpperCase = false;
	public static boolean foundLowerCase = false;
	public static boolean foundNumericDigit = false;
	public static boolean foundSpecialChar = false;
	public static boolean foundLongEnough = false;

	private static String inputLine = "";
	private static char currentChar;
	private static int currentCharNdx;
	private static boolean running;


	/**********
	 * <p> Title: evaluatePassword - Public Method </p>
	 * 
	 * <p> Description: This method evaluates the specified password using the same requirements
	 * as PasswordEvaluationTestbed-F26.
	 * 
	 * The password must contain at least one upper case letter, one lower case letter, one
	 * numeric digit, one special character, and at least eight characters.</p>
	 * 
	 * @param input		The password string to be evaluated
	 * 
	 * @return			An empty String if the password satisfies all requirements.  If the
	 * 					password is invalid, a String describing the problem is returned.
	 */
	public static String evaluatePassword(String input) {

		// Initialize the evaluator state
		passwordErrorMessage = "";
		passwordIndexofError = 0;
		inputLine = input;
		currentCharNdx = 0;

		// A password must contain at least one character
		if (input.length() <= 0) {
			return "*** Error *** The password is empty!";
		}

		// The input is not empty, so obtain the first character
		currentChar = input.charAt(0);

		// Save a copy of the password being evaluated
		passwordInput = input;

		// Reset all requirement flags
		foundUpperCase = false;
		foundLowerCase = false;
		foundNumericDigit = false;
		foundSpecialChar = false;
		foundLongEnough = false;

		// Begin processing the password
		running = true;

		while (running) {

			// Determine which valid character class contains the current character
			if (currentChar >= 'A' && currentChar <= 'Z') {
				foundUpperCase = true;
			}

			else if (currentChar >= 'a' && currentChar <= 'z') {
				foundLowerCase = true;
			}

			else if (currentChar >= '0' && currentChar <= '9') {
				foundNumericDigit = true;
			}

			else if ("~`!@#$%^&*()_-+={}[]|\\:;\"'<>,.?/"
					.indexOf(currentChar) >= 0) {

				foundSpecialChar = true;
			}

			else {
				// The current character does not belong to an allowed character class
				passwordIndexofError = currentCharNdx;

				return "*** Error *** An invalid character has been found!";
			}

			// A password must contain at least eight characters
			if (currentCharNdx >= 7) {
				foundLongEnough = true;
			}

			// Advance to the next character, if there is one
			currentCharNdx++;

			if (currentCharNdx >= inputLine.length()) {
				running = false;
			}

			else {
				currentChar = input.charAt(currentCharNdx);
			}
		}

		// Construct a message listing any requirements that were not satisfied
		String errMessage = "";

		if (!foundUpperCase)
			errMessage += "Upper case; ";

		if (!foundLowerCase)
			errMessage += "Lower case; ";

		if (!foundNumericDigit)
			errMessage += "Numeric digit(s); ";

		if (!foundSpecialChar)
			errMessage += "Special character; ";

		if (!foundLongEnough)
			errMessage += "Long Enough; ";

		// An empty error message means all requirements were satisfied
		if (errMessage.equals(""))
			return "";

		// At least one password requirement was not satisfied
		passwordIndexofError = currentCharNdx;

		return "Password is missing: " + errMessage;
	}
}