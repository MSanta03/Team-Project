package guiNewAccount;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;
import passwordEvaluator.PasswordEvaluator;
import userNameRecognizer.UserNameRecognizer;

/*******
 * <p> Title: ControllerNewAccount Class. </p>
 * 
 * <p> Description: The Java/FX-based New Account Page.  This class provides the controller actions
 * to allow the user to establish a new account after responding to an invitation and the use of a
 * one time code.
 * 
 * The controller deals with the user pressing the "User Step" button widget being click.  If also
 * supports the user click on the "Quit" button widget.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter & Jacob Yates
 * 
 * @version 1.00		2025-08-17 Initial version
 * 
 * @version 1.01		2026-09-19 Input validation added
 *  
 */

public class ControllerNewAccount {
	
	/*-********************************************************************************************

	The User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	/**
	 * Default constructor is not used.
	 */
	public ControllerNewAccount() {
	}
	
	
	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**********
	 * <p> Method: public doCreateUser() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the User Setup
	 * button.  This method checks the input fields to see that they are valid.  If so, it then
	 * creates the account by adding information to the database.
	 * 
	 * The method reaches batch to the view page and to fetch the information needed rather than
	 * passing that information as parameters.
	 * 
	 */	
	protected static void doCreateUser() {

		// Fetch the username and both password fields from the View.
		String username = ViewNewAccount.text_Username.getText();
		String password = ViewNewAccount.text_Password1.getText();
		String confirmPassword = ViewNewAccount.text_Password2.getText();

		// Validate the username before it is used to create the account.
		String usernameEvaluation = UserNameRecognizer.checkForValidUserName(username);
		if (!usernameEvaluation.equals("")) {
			ViewNewAccount.alertUsernamePasswordError.setTitle("Invalid Username");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(
					"The username does not satisfy the username requirements.");
			ViewNewAccount.alertUsernamePasswordError.setContentText(usernameEvaluation);
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Do not allow a username that is already registered.
		if (theDatabase.doesUserExist(username)) {
			ViewNewAccount.alertUsernamePasswordError.setTitle("Username Already Exists");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(null);
			ViewNewAccount.alertUsernamePasswordError.setContentText(
					"That username is already in use. Please choose another username.");
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Check the maximum password input size before using the password.
		if (password.length() > 255 || confirmPassword.length() > 255) {
			ViewNewAccount.alertUsernamePasswordError.setTitle("Invalid Password");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(null);
			ViewNewAccount.alertUsernamePasswordError.setContentText(
					"The password is too long. The password may not exceed 255 characters.");
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Do not allow either password field to be empty.
		if (password.length() == 0 || confirmPassword.length() == 0) {
			ViewNewAccount.alertUsernamePasswordError.setTitle("Invalid Password");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(null);
			ViewNewAccount.alertUsernamePasswordError.setContentText(
					"Enter the password in both password fields and try again.");
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Make sure the two password fields contain the same password.
		if (!password.equals(confirmPassword)) {
			ViewNewAccount.text_Password1.setText("");
			ViewNewAccount.text_Password2.setText("");
			ViewNewAccount.alertUsernamePasswordError.setTitle("Passwords Do Not Match");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(
					"The two passwords must be identical.");
			ViewNewAccount.alertUsernamePasswordError.setContentText(
					"Correct the passwords and try again.");
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Evaluate the permanent password using the team's password rules.
		String passwordEvaluation = PasswordEvaluator.evaluatePassword(password);
		if (!passwordEvaluation.equals("")) {
			ViewNewAccount.alertUsernamePasswordError.setTitle("Invalid Password");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(
					"The password does not satisfy the password requirements.");
			ViewNewAccount.alertUsernamePasswordError.setContentText(passwordEvaluation);
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// Display key information to the log after the input has been validated.
		System.out.println("** Account for Username: " + username + "; theInvitationCode: "+
				ViewNewAccount.theInvitationCode + "; email address: " + 
				ViewNewAccount.emailAddress + "; Role: " + ViewNewAccount.theRole);

		int roleCode = 0;
		User user = null;

		// Set up the role and User object based on the information in the invitation.
		if (ViewNewAccount.theRole.compareTo("Admin") == 0) {
			roleCode = 1;
			user = new User(username, password, "", "", "", "", "", true, false, false);
		} else if (ViewNewAccount.theRole.compareTo("Role1") == 0) {
			roleCode = 2;
			user = new User(username, password, "", "", "", "", "", false, true, false);
		} else if (ViewNewAccount.theRole.compareTo("Role2") == 0) {
			roleCode = 3;
			user = new User(username, password, "", "", "", "", "", false, false, true);
		} else {
			System.out.println(
					"**** Trying to create a New Account for a role that does not exist!");
			return;
		}

		// Unlike the FirstAdmin, we know the email address, so set that into the user as well.
		user.setEmailAddress(ViewNewAccount.emailAddress);

		// Inform the system about which role will be played.
		applicationMain.FoundationsMain.activeHomePage = roleCode;

		// Create the account based on the validated User object.
		try {
			theDatabase.register(user);
		} catch (SQLException e) {
			System.err.println("*** ERROR *** Database error: " + e.getMessage());
			e.printStackTrace();
			ViewNewAccount.alertUsernamePasswordError.setTitle("Account Creation Error");
			ViewNewAccount.alertUsernamePasswordError.setHeaderText(null);
			ViewNewAccount.alertUsernamePasswordError.setContentText(
					"The account could not be created. Please try again.");
			ViewNewAccount.alertUsernamePasswordError.showAndWait();
			return;
		}

		// The account has been set, so remove the invitation from the system.
		theDatabase.removeInvitationAfterUse(
				ViewNewAccount.text_Invitation.getText());

		// Set the database so it has this user as the current user.
		theDatabase.getUserAccountDetails(username);

		// Navigate to the User Update page.
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewNewAccount.theStage, user);
	}

	
	/**********
	 * <p> Method: public performQuit() </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the Quit button.  Doing
	 * this terminates the execution of the application.  All important data must be stored in the
	 * database, so there is no cleanup required.  (This is important so we can minimize the impact
	 * of crashed.)
	 * 
	 */	
	protected static void performQuit() {
		System.out.println("Perform Quit");
		System.exit(0);
	}	
}
