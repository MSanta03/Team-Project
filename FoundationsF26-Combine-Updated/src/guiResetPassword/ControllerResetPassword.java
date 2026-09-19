package guiResetPassword;

import database.Database;
import passwordEvaluator.PasswordEvaluator;

/********
 * <p> Title: ControllerResetPassword Class. </p>
 * 
 * <p> Description: The Java/FX-based Password Reset Page. This class provides the controller actions
 * to allow the user to reset their password after the admin has given them a one-time password to use when
 * they cannot remember their login information.
 * 
 * The controller handles validating the two passwords match and updating the database to update the passwords
 * The class also ensures the user is returned to the login page so they can login using their new information.
 * 
 * The class has been written assuming the View or the Model are the only class methods that invoke
 * these methods. This is why each has been declared as "protected".</p> 
 * 
 * <p> Copyright: Marcy Santana & Jacob Yates © 2026 </p>
 * 
 * @author Marcy Santana and Jacob Yates
 * 
 * @version 1.00 2026-09-14 Initial Version
 * */

public class ControllerResetPassword {
	
	/*
	 * This controller is never instantiated, which is why it uses only static methods which are
	 * called by the View.
	 * */
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/*
	 * Default constructor is not used.
	 * */
	public ControllerResetPassword() {
	}
	
	/*******
	 * <p> Method: public doPasswordReset(String username) </p>
	 * 
	 * <p> Description: This method processes the user's request to establish a new permanent
	 * password after logging in with a one-time password. The method retrieves the new password
	 * and confirmation password from the View and validates the input before making any changes
	 * to the database. </p>
	 * 
	 *  <p> If all validation succeeds, the user's permanent password is updated in the database.
	 * The user is then returned to the Login page and must log in again using the newly
	 * established permanent password. </p>
	 * 
	 * @param username specifies the username of the account that needs to reset their password.
	 * */
	protected static void doPasswordReset(String username) {
		String newPassword = ViewResetPassword.text_Password1.getText();
		String confirmPassword = ViewResetPassword.text_Password2.getText();

		// Check the maximum input size before using the password.
		if (newPassword.length() > 255 || confirmPassword.length() > 255) {
			ViewResetPassword.alertPasswordError.setTitle("Invalid Password");
			ViewResetPassword.alertPasswordError.setHeaderText(null);
			ViewResetPassword.alertPasswordError.setContentText(
					"The password is too long. The password may not exceed 255 characters.");
			ViewResetPassword.alertPasswordError.showAndWait();
			return;
		}

		// Do not allow an empty password.
		if (newPassword.length() == 0) {
			ViewResetPassword.alertPasswordError.setTitle("Invalid Password");
			ViewResetPassword.alertPasswordError.setHeaderText(null);
			ViewResetPassword.alertPasswordError.setContentText(
					"Enter a new password and try again.");
			ViewResetPassword.alertPasswordError.showAndWait();
			return;
		}

		// Do not allow an empty confirmation password.
		if (confirmPassword.length() == 0) {
			ViewResetPassword.alertPasswordError.setTitle("Invalid Password");
			ViewResetPassword.alertPasswordError.setHeaderText(null);
			ViewResetPassword.alertPasswordError.setContentText(
					"Enter the new password again to confirm it.");
			ViewResetPassword.alertPasswordError.showAndWait();
			return;
		}

		// Ensure that the two password fields are identical.
		if (!newPassword.equals(confirmPassword)) {
			ViewResetPassword.text_Password1.setText("");
			ViewResetPassword.text_Password2.setText("");
			ViewResetPassword.alertPasswordError.setTitle("Passwords Do Not Match");
			ViewResetPassword.alertPasswordError.setHeaderText(
					"The two passwords must be identical.");
			ViewResetPassword.alertPasswordError.setContentText(
					"Correct the passwords and try again.");
			ViewResetPassword.alertPasswordError.showAndWait();
			return;
		}

		// Evaluate the new password using the PasswordEvaluationTestbed-F26 rules.
		String passwordEvaluation = PasswordEvaluator.evaluatePassword(newPassword);

		// An empty String means all password requirements were satisfied.
		if (!passwordEvaluation.equals("")) {
			ViewResetPassword.alertPasswordError.setTitle("Invalid Password");
			ViewResetPassword.alertPasswordError.setHeaderText(
					"The password does not satisfy the password requirements.");
			ViewResetPassword.alertPasswordError.setContentText(passwordEvaluation);
			ViewResetPassword.alertPasswordError.showAndWait();
			return;
		}

		// All validation has succeeded, so update the permanent password.
		if (theDatabase.updatePassword(username, newPassword)) {
			// Require the user to log in again using the new permanent password.
			guiUserLogin.ViewUserLogin.displayUserLogin(ViewResetPassword.theStage);
		}
		else {
			ViewResetPassword.alertPasswordError.setTitle("Password Update Error");
			ViewResetPassword.alertPasswordError.setHeaderText(null);
			ViewResetPassword.alertPasswordError.setContentText(
					"The password could not be updated. Please try again.");
			ViewResetPassword.alertPasswordError.showAndWait();
		}
	}

	/**********
	 * <p> Method: public performQuit() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Quit button.  Doing
	 * this terminates the execution of the application.  All important data must be stored in the
	 * database, so there is no cleanup required.  (This is important so we can minimize the impact
	 * of crashed.)
	 * */
	 protected static void performQuit() {
		System.out.println("Perform Quit.");
		System.exit(0);
	}
}