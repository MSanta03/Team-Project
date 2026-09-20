package guiOneTimePassword;

import java.util.List;

import database.Database;
import entityClasses.User;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*******
 * <p> Title: ViewOneTimePassword Class. </p>
 * 
 * <p> Description: The Java FX based page that allows an admin to establish a one time
 * password for an existing user account.</p>
 * 
 * <p> The one time password is associated with the selected users account and is stored
 * separately from the users permanent password.  The one time password may then be used
 * once by that specific user to establish a new permanent password.</p>
 * 
 * @author Team
 * 
 * @version 1.00		2026-09-13 Initial version
 *  
 */

public class ViewOneTimePassword {

	/*-********************************************************************************************

	Attributes

	 */

	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	
	// GUI Area 1: Page title and information about the current Admin
	
	protected static Label label_PageTitle = new Label("Set One-Time Password");
	protected static Label label_UserDetails = new Label();
	
	// This separator partitions the heading from the page-specific controls
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);
	
	
	// GUI Area 2: Select the user and establish a one time password
	
	protected static Label label_SelectUser =
			new Label("Select the user who needs a one-time password:");
	
	protected static ComboBox<String> combobox_SelectUser =
			new ComboBox<String>();
	
	protected static Label label_OneTimePassword =
			new Label("Enter the one-time password:");
	
	protected static PasswordField text_OneTimePassword =
			new PasswordField();
	
	protected static Label label_ConfirmOneTimePassword =
			new Label("Confirm the one-time password:");
	
	protected static PasswordField text_ConfirmOneTimePassword =
			new PasswordField();
	
	protected static Button button_SetOneTimePassword =
			new Button("Set One-Time Password");
	
	protected static Alert alertError =
			new Alert(AlertType.INFORMATION);
	
	protected static Alert alertSuccess =
			new Alert(AlertType.INFORMATION);
	
	
	// This separator partitions the work area from the navigation controls
	
	protected static Line line_Separator2 =
			new Line(20, 525, width-20, 525);
	
	
	// GUI Area 3: Navigation
	
	protected static Button button_Return = new Button("Return");
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");
	
	
	// References used by this page
	
	private static ViewOneTimePassword theView;
	
	private static Database theDatabase =
			applicationMain.FoundationsMain.database;
	
	protected static Stage theStage;
	protected static Pane theRootPane;
	protected static User theUser;
	
	public static Scene theOneTimePasswordScene = null;
	
	
	/*-********************************************************************************************

	Constructors

	 */

	/**********
	 * <p> Method: displayOneTimePassword(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single point from outside this package to cause
	 * the Set One Time Password page to be displayed.
	 * 
	 * It establishes the stage and current user references and if needed creates the
	 * instance of this view.  It then refreshes the user list and clears the password fields so
	 * information from a previous use of this page is not still there.</p>
	 * 
	 * @param ps specifies the Java FX stage to be used for this GUI and its methods
	 * 
	 * @param user specifies the current admin user of the application
	 * 
	 */
	public static void displayOneTimePassword(Stage ps, User user) {
		
		// Establish references to the current Stage and user
		theStage = ps;
		theUser = user;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null)
			theView = new ViewOneTimePassword();
		
		// Refresh the list of current users in case the database has changed
		List<String> userList = theDatabase.getUserList();
		combobox_SelectUser.setItems(FXCollections.observableArrayList(userList));
		combobox_SelectUser.getSelectionModel().select(0);
		
		// Clear password information from any previous use of this page
		text_OneTimePassword.setText("");
		text_ConfirmOneTimePassword.setText("");
		
		// Populate the current admin information
		label_UserDetails.setText("User: " + theUser.getUserName());
		
		// Set the title for the window, display the page, and wait for the admin to act
		theStage.setTitle(
				"CSE 360 Foundation Code: Set One-Time Password Page");
		theStage.setScene(theOneTimePasswordScene);
		theStage.show();
	}
	
	
	/**********
	 * <p> Method: ViewOneTimePassword() </p>
	 * 
	 * <p> Description: This constructor initializes all of the graphical user interface elements
	 * for the Set One-Time Password page.  This includes location, size, font, alignment, and
	 * event handlers for each GUI object.</p>
	 * 
	 */
	private ViewOneTimePassword() {
		
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theOneTimePasswordScene =
				new Scene(theRootPane, width, height);
		
		
		// GUI Area 1
		
		setupLabelUI(
				label_PageTitle, "Arial", 28,
				width, Pos.CENTER, 0, 5);
		
		setupLabelUI(
				label_UserDetails, "Arial", 20,
				width, Pos.BASELINE_LEFT, 20, 55);
		
		
		// GUI Area 2
		
		setupLabelUI(
				label_SelectUser, "Arial", 18,
				450, Pos.BASELINE_LEFT, 20, 130);
		
		setupComboBoxUI(
				combobox_SelectUser, "Dialog", 16,
				300, 20, 170);
		
		setupLabelUI(
				label_OneTimePassword, "Arial", 18,
				350, Pos.BASELINE_LEFT, 20, 230);
		
		setupTextUI(
				text_OneTimePassword, "Arial", 18,
				300, Pos.BASELINE_LEFT, 20, 270, true);
		
		text_OneTimePassword.setPromptText(
				"Enter One-Time Password");
		
		setupLabelUI(
				label_ConfirmOneTimePassword, "Arial", 18,
				350, Pos.BASELINE_LEFT, 20, 330);
		
		setupTextUI(
				text_ConfirmOneTimePassword, "Arial", 18,
				300, Pos.BASELINE_LEFT, 20, 370, true);
		
		text_ConfirmOneTimePassword.setPromptText(
				"Confirm One-Time Password");
		
		setupButtonUI(
				button_SetOneTimePassword, "Dialog", 18,
				240, Pos.CENTER, 400, 370);
		
		button_SetOneTimePassword.setOnAction((_) -> {
			ControllerOneTimePassword.setOneTimePassword();
		});
		
		
		// Configure the alerts
		
		alertError.setTitle("Unable to Set One-Time Password");
		alertError.setHeaderText(null);
		
		alertSuccess.setTitle("One-Time Password Established");
		alertSuccess.setHeaderText(null);
		
		
		// GUI Area 3
		
		setupButtonUI(
				button_Return, "Dialog", 18,
				210, Pos.CENTER, 20, 540);
		
		button_Return.setOnAction((_) -> {
			ControllerOneTimePassword.performReturn();
		});
		
		setupButtonUI(
				button_Logout, "Dialog", 18,
				210, Pos.CENTER, 300, 540);
		
		button_Logout.setOnAction((_) -> {
			ControllerOneTimePassword.performLogout();
		});
		
		setupButtonUI(
				button_Quit, "Dialog", 18,
				210, Pos.CENTER, 580, 540);
		
		button_Quit.setOnAction((_) -> {
			ControllerOneTimePassword.performQuit();
		});
		
		
		// Add all widgets to the Pane
		
		theRootPane.getChildren().addAll(
				label_PageTitle,
				label_UserDetails,
				line_Separator1,
				label_SelectUser,
				combobox_SelectUser,
				label_OneTimePassword,
				text_OneTimePassword,
				label_ConfirmOneTimePassword,
				text_ConfirmOneTimePassword,
				button_SetOneTimePassword,
				line_Separator2,
				button_Return,
				button_Logout,
				button_Quit);
	}
	
	
	/*-********************************************************************************************

	Helper methods to reduce code length

	 */

	/**********
	 * Private local method to initialize the standard fields for a label
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		
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
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 * 
	 * @param b		The TextField object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 * @param e		Is this TextField user editable?
	 */
	private void setupTextUI(PasswordField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a ComboBox
	 * 
	 * @param c		The ComboBox object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the ComboBox
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupComboBoxUI(ComboBox<String> c, String ff, double f, double w, double x, double y) {
		
		c.setStyle("-fx-font: " + f + "px \"" + ff + "\";");
		c.setMinWidth(w);
		c.setMaxWidth(w);
		c.setLayoutX(x);
		c.setLayoutY(y);
	}
}