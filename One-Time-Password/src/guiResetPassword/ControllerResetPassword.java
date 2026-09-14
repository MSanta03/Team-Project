package guiResetPassword;

import java.sql.SQLException;

import database.Database;
import entityClasses.User;
import guiResetPassword.ViewResetPassword;

public class ControllerResetPassword {
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	public ControllerResetPassword() {
	}
	
	protected static void doPasswordReset(String username) {
		// Make sure the two passwords are the same.	
		if (ViewResetPassword.text_Password1.getText().
				compareTo(ViewResetPassword.text_Password2.getText()) == 0) {


        	// Create a new User object with the pre-set role and register in the database
        	theDatabase.updatePassword(username, ViewResetPassword.text_Password1.getText());
       

            // Navigate to the Welcome Login Page
            guiUserLogin.ViewUserLogin.displayUserLogin(ViewResetPassword.theStage);
		}
		else {
			// The two passwords are NOT the same, so clear the passwords, explain the passwords
			// must be the same, and clear the message as soon as the first character is typed.
			ViewResetPassword.text_Password1.setText("");
			ViewResetPassword.text_Password2.setText("");
			ViewResetPassword.alertUsernamePasswordError.showAndWait();
		}
	}
	
	protected static void performQuit() {
		System.out.println("Perform Quit.");
		System.exit(0);
	}
}