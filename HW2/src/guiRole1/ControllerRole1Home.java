package guiRole1;

import java.sql.SQLException;

import entityClasses.Lesson;
import entityClasses.LessonList;
import lessonValidator.LessonValidator;

/*******
 * <p> Title: ControllerRole1Home Class. </p>
 *
 * <p> Description: The Java/FX-based Role 1 Home Page.  This class provides the controller
 * actions basic on the user's use of the JavaFX GUI widgets defined by the View class.
 *
 * This page is a stub for establish future roles for the application.
 *
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 1.00		2025-08-17 Initial version
 * @version 1.01		2025-09-16 Update Javadoc documentation
 * @version 1.02		2026-10-07 Added lesson CRUD actions for HW2
 */

public class ControllerRole1Home {

	private static LessonList currentLessons = new LessonList();

	/*-*******************************************************************************************

	User Interface Actions for this page

	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and
	the Model is often just a stub, or will be a singleton instantiated object.

	 */

	/**
	 * Default constructor is not used.
	 */
	public ControllerRole1Home() {
	}

	/**********
	 * <p> Method: performUpdate() </p>
	 *
	 * <p> Description: This method directs the user to the User Update Page so the user can change
	 * the user account attributes. </p>
	 *
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewRole1Home.theStage, ViewRole1Home.theUser);
	}

	/**
	 * Creates a new lesson using the information entered on the Role1 Home
	 * page. The input is validated before the database operation so invalid
	 * lesson information is not stored.
	 */
	protected static void performCreateLesson() {
		String title = ViewRole1Home.text_LessonTitle.getText();
		String description = ViewRole1Home.text_LessonDescription.getText();
		String errorMessage = LessonValidator.validateTitle(title);

		if (!errorMessage.isEmpty()) {
			ViewRole1Home.label_LessonError.setText(errorMessage);
			return;
		}

		errorMessage = LessonValidator.validateDescription(description);

		if (!errorMessage.isEmpty()) {
			ViewRole1Home.label_LessonError.setText(errorMessage);
			return;
		}

		Lesson lesson = new Lesson(0, ViewRole1Home.theUser.getUserName(), title, description);
		try {
			ViewRole1Home.theDatabase.createLesson(lesson);
			ViewRole1Home.text_LessonTitle.clear();
			ViewRole1Home.text_LessonDescription.clear();
			refreshLessons();
			ViewRole1Home.label_LessonError.setText("Lesson created successfully.");
		}
		catch (SQLException e) {
			ViewRole1Home.label_LessonError.setText("Unable to create lesson.");
		}
	}

	/**
	 * Loads the lessons belonging to the current contributor from the database
	 * and displays their titles in the lesson list.
	 */
	protected static void refreshLessons() {
		try {
			currentLessons = ViewRole1Home.theDatabase.getLessonsByContributor(
					ViewRole1Home.theUser.getUserName());

			ViewRole1Home.list_Lessons.getItems().clear();

			for (Lesson lesson : currentLessons.getLessons()) {
				ViewRole1Home.list_Lessons.getItems().add(lesson.getTitle());
			}
		}
		catch (SQLException e) {
			ViewRole1Home.label_LessonError.setText("Unable to load lessons.");
		}
	}

	/**
	 * Displays the information for the lesson selected from the lesson list.
	 */
	protected static void performLessonSelection() {
		int selectedIndex = ViewRole1Home.list_Lessons.getSelectionModel().getSelectedIndex();

		if (selectedIndex >= 0) {
			Lesson lesson = currentLessons.getLessons().get(selectedIndex);

			ViewRole1Home.text_LessonTitle.setText(lesson.getTitle());
			ViewRole1Home.text_LessonDescription.setText(lesson.getDescription());
		}
	}

	/**
	 * Updates the lesson selected from the lesson list.
	 */
	protected static void performUpdateLesson() {
		int selectedIndex = ViewRole1Home.list_Lessons.getSelectionModel().getSelectedIndex();

		if (selectedIndex < 0) {
			ViewRole1Home.label_LessonError.setText("Select a lesson to update.");
			return;
		}

		String title = ViewRole1Home.text_LessonTitle.getText();
		String description = ViewRole1Home.text_LessonDescription.getText();
		String errorMessage = LessonValidator.validateTitle(title);

		if (!errorMessage.isEmpty()) {
			ViewRole1Home.label_LessonError.setText(errorMessage);
			return;
		}

		errorMessage = LessonValidator.validateDescription(description);

		if (!errorMessage.isEmpty()) {
			ViewRole1Home.label_LessonError.setText(errorMessage);
			return;
		}

		Lesson selectedLesson = currentLessons.getLessons().get(selectedIndex);
		Lesson updatedLesson = new Lesson(selectedLesson.getLessonID(),
				ViewRole1Home.theUser.getUserName(), title, description);

		try {
			if (ViewRole1Home.theDatabase.updateLesson(updatedLesson)) {
				refreshLessons();
				ViewRole1Home.label_LessonError.setText("Lesson updated successfully.");
			}
			else {
				ViewRole1Home.label_LessonError.setText("Lesson could not be found.");
			}
		}
		catch (SQLException e) {
			ViewRole1Home.label_LessonError.setText("Unable to update lesson.");
		}
	}

	/**
	 * Deletes the lesson selected from the lesson list.
	 */
	protected static void performDeleteLesson() {
		int selectedIndex = ViewRole1Home.list_Lessons.getSelectionModel().getSelectedIndex();

		if (selectedIndex < 0) {
			ViewRole1Home.label_LessonError.setText("Select a lesson to delete.");
			return;
		}

		Lesson lesson = currentLessons.getLessons().get(selectedIndex);

		try {
			if (ViewRole1Home.theDatabase.deleteLesson(lesson.getLessonID(),
					ViewRole1Home.theUser.getUserName())) {
				ViewRole1Home.text_LessonTitle.clear();
				ViewRole1Home.text_LessonDescription.clear();
				refreshLessons();
				ViewRole1Home.label_LessonError.setText("Lesson deleted successfully.");
			}
			else {
				ViewRole1Home.label_LessonError.setText("Lesson could not be found.");
			}
		}
		catch (SQLException e) {
			ViewRole1Home.label_LessonError.setText("Unable to delete lesson.");
		}
	}

	/**********
	 * <p> Method: performLogout() </p>
	 *
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 *
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewRole1Home.theStage);
	}

	/**********
	 * <p> Method: performQuit() </p>
	 *
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 *
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}
