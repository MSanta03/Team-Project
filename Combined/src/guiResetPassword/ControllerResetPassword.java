package guiResetPassword;

import database.Database;

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
 * <p> Copyright: Marcy Santana © 2026 </p>
 * 
 * @author Marcy Santana
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
	 * <p> Description: This method is called when the user clicks the reset password button.
	 * This method checks that the two password input fields match one another, and if so
	 * it updates the user's password in the database and returns them to the user login page.
	 * 
	 * @param username specifies the username of the account that needs to reset their password.
	 * */
	protected static void doPasswordReset(String username) {
		// Make sure the two passwords are the same.	
		if (ViewResetPassword.text_Password1.getText().
				compareTo(ViewResetPassword.text_Password2.getText()) == 0) {


			// update the user's information in the database.
        	theDatabase.updatePassword(username, ViewResetPassword.text_Password1.getText());
       

            // Navigate to the Login Page
            guiUserLogin.ViewUserLogin.displayUserLogin(ViewResetPassword.theStage);
		}
		else {
			// The two passwords are NOT the same, so clear the passwords, explain the passwords
			// must be the same, and clear the message as soon as the first character is typed.
			ViewResetPassword.text_Password1.setText("");
			ViewResetPassword.text_Password2.setText("");
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