package guiResetPassword;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*******
 * <p> Title: ViewResetPassword Class. </p>
 * 
 * <p> Description: The ViewResetPassword Page is used to enable a user to reset their password after 
 * the admin has issued a one-time password, in the even the user can't remember their login information. </p>
 * 
 * <p> Copyright: Marcy Santana © 2026 </p>
 * 
 * @author Marcy Santana
 * 
 * @version 1.00		2026-09-14 Initial version
 *  
 */

public class ViewResetPassword {
	
	/*-********************************************************************************************

	Attributes
	
	*/
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	// This is a simple GUI password reset Page, very similar to the NewUserLogin Page.
	private static Label label_ApplicationTitle = 
			new Label("Foundation Application Password Reset Page");
    protected static Label label_PasswordResetCreation = new Label("Password Reset.");
    protected static Label label_PasswordResetLine = new Label("Please enter a new password.");
    protected static PasswordField text_Password1 = new PasswordField();
    protected static PasswordField text_Password2 = new PasswordField();
    protected static Button button_PasswordReset = new Button("Reset Password");

	// This alert is used should the user enter two passwords that do not match
	protected static Alert alertPasswordError = new Alert(AlertType.INFORMATION);

    protected static Button button_Quit = new Button("Quit");

	// These attributes are used to configure the page and populate it with this user's information
	private static ViewResetPassword theView;		// Is instantiation of the class needed?

	protected static Stage theStage;			// The Stage that JavaFX has established for us
	private static Pane theRootPane;			// The Pane that holds all the GUI widgets 
   
    protected static String username;					// username of the account that needs its password reset
	public static Scene thePasswordResetScene = null;	// Access to the User Update page's GUI Widgets
	

	/*-********************************************************************************************

	Constructors
	
	*/

	/**********
	 * <p> Method: displayResetPassword(Stage ps, String ic) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the PasswordReset page to be displayed.
	 * 
	 * It first sets up very shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param ic specifies the user's invitation code for this GUI and it's methods
	 * 
	 */
	public static void displayResetPassword(Stage ps, String un) {
		// This is the only way some component of the system can cause a Password Reset page to
		// appear.  The first time, the class is created and initialized.  Every subsequent call it
		// is reused with only the elements that differ being initialized.
		
		// Establish the references to the GUI and the current user
		theStage = ps;				// Save the reference to the Stage for the rest of this package
		username = un;
		
		if (theView == null) theView = new ViewResetPassword();
		
		text_Password1.setText("");	// appear for a new user
		text_Password2.setText("");
	
		
    	// Place all of the established GUI elements into the pane
    	theRootPane.getChildren().clear();
    	theRootPane.getChildren().addAll(label_PasswordResetCreation, label_PasswordResetLine,
    			text_Password1, text_Password2, button_PasswordReset, button_Quit);    	

		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundation Code: New User Account Setup");	
        theStage.setScene(thePasswordResetScene);
		theStage.show();
	}
	
	/**********
	 * <p> Constructor: ViewResetPassword() </p>
	 * 
	 * <p> Description: This constructor is called just once, the first time a new account needs to
	 * be created.  It establishes all of the common GUI widgets for the page so they are only
	 * created once and reused when needed.
	 * 
	 * The do
	 * 		
	 */
	private ViewResetPassword() {
		
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		thePasswordResetScene = new Scene(theRootPane, width, height);

		// Label the Panel with the name of the startup screen, centered at the top of the pane
		setupLabelUI(label_ApplicationTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
		
    	// Label to display the welcome message for the new user
    	setupLabelUI(label_PasswordResetCreation, "Arial", 32, width, Pos.CENTER, 0, 10);
	
    	// Label to display the  message for the first user
    	setupLabelUI(label_PasswordResetLine, "Arial", 24, width, Pos.CENTER, 0, 70);
		
		// Establish the text input operand field for the password
		setupTextUI(text_Password1, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_Password1.setPromptText("Enter the Password");
		
		// Establish the text input operand field to confirm the password
		setupTextUI(text_Password2, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, true);
		text_Password2.setPromptText("Enter the Password Again");
		
		// setup password reset and return to login page
	    setupButtonUI(button_PasswordReset, "Dialog", 18, 200, Pos.CENTER, 475, 210);
        button_PasswordReset.setOnAction((_) -> {ControllerResetPassword.doPasswordReset(username); });
        
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((_) -> {ControllerResetPassword.performQuit(); });

		// If the passwords do not match, this alert dialog will tell the user
		alertPasswordError.setTitle("Passwords Do Not Match");
		alertPasswordError.setHeaderText("The two passwords must be identical.");
		alertPasswordError.setContentText("Correct the passwords and try again.");
	}
	
	
	/*-********************************************************************************************

	Helper methods to reduce code length

	 */
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}	
}
