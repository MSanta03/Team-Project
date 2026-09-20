package guiOneTimePassword;

import database.Database;

/*******
 * <p> Title: ControllerOneTimePassword Class. </p>
 * 
 * <p> Description: The Java/FX-based Set One-Time Password Page.  This class provides the
 * controller actions based on the Admin user's use of the JavaFX GUI widgets defined by the
 * View class.
 * 
 * This controller allows an Admin to select an existing user and establish a one-time password
 * for that specific account.  The password is stored separately from the user's permanent
 * password. </p>
 * 
 * @author Jacob Yates
 * 
 * @version 1.00		2026-09-13 Initial version
 */


public class ControllerOneTimePassword {

	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View.
	
	*/

	/**
	 * Default constructor is not used.
	 */
	public ControllerOneTimePassword() {
	}

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	
	/**********
	 * <p> Method: setOneTimePassword() </p>
	 * 
	 * <p> Description: This method is called when the Admin has selected a user, entered a
	 * one-time password, confirmed the password, and clicked the button to establish the
	 * one-time password. </p>
	 * 
	 */
	protected static void setOneTimePassword() {
		
		String username = (String) ViewOneTimePassword.combobox_SelectUser.getValue();
		String oneTimePassword = ViewOneTimePassword.text_OneTimePassword.getText();
		String confirmOneTimePassword =
				ViewOneTimePassword.text_ConfirmOneTimePassword.getText();
		
		// Ensure that an actual user has been selected
		if (username == null || username.equals("<Select a User>")) {
			ViewOneTimePassword.alertError.setContentText(
					"Select a user before setting a one-time password.");
			ViewOneTimePassword.alertError.showAndWait();
			return;
		}
		
		// Check that a one-time password has been provided
		if (oneTimePassword.length() == 0) {
			ViewOneTimePassword.alertError.setContentText(
					"Enter a one-time password and try again.");
			ViewOneTimePassword.alertError.showAndWait();
			return;
		}
		
		// Check the input size before doing anything else with the password
		if (oneTimePassword.length() > 255 ||
				confirmOneTimePassword.length() > 255) {
			ViewOneTimePassword.alertError.setContentText(
					"The one-time password is too long.");
			ViewOneTimePassword.alertError.showAndWait();
			return;
		}
		
		// Ensure that the two passwords are identical
		if (!oneTimePassword.equals(confirmOneTimePassword)) {
			ViewOneTimePassword.alertError.setContentText(
					"The two one-time passwords must match.");
			ViewOneTimePassword.alertError.showAndWait();
			return;
		}
		
		// Store the one-time password for the selected user
		if (theDatabase.setOneTimePassword(username, oneTimePassword)) {
			ViewOneTimePassword.alertSuccess.setContentText(
					"A one-time password has been established for " + username + ".");
			ViewOneTimePassword.alertSuccess.showAndWait();
			
			ViewOneTimePassword.text_OneTimePassword.setText("");
			ViewOneTimePassword.text_ConfirmOneTimePassword.setText("");
		}
		
		else {
			ViewOneTimePassword.alertError.setContentText(
					"The one-time password could not be established.");
			ViewOneTimePassword.alertError.showAndWait();
		}
	}
	
	
	/**********
	 * <p> Method: performReturn() </p>
	 * 
	 * <p> Description: Protected method that returns the Admin user to the Admin Home Page.</p>
	 * 
	 */
	protected static void performReturn() {
		guiAdminHome.ViewAdminHome.displayAdminHome(
				ViewOneTimePassword.theStage, ViewOneTimePassword.theUser);
	}
	
	
	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal Login
	 * page.</p>
	 * 
	 */
	protected static void performLogout() {
		
		guiUserLogin.ViewUserLogin.displayUserLogin(
				ViewOneTimePassword.theStage);
	}
	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates execution of the program.</p>
	 * 
	 */
	protected static void performQuit() {
		
		System.exit(0);
	}
}
