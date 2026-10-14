package lessonsLearnedTestAutomation;

import java.sql.SQLException;

import database.Database;
import entityClasses.LessonLearned;
import entityClasses.User;
import lessonsLearnedViewer.LessonsLearnedViewer;
import lessonsLearnedUpdater.LessonsLearnedUpdater;
import lessonsLearnedDeleter.LessonsLearnedDeleter;
import java.util.List;

/**
 * Title: LessonsLearnedTestAutomation
 * 
 *  Description: A testbed for ensuring lessons learned functionality meets user story requirements by implementing 
 *  derived test cases.
 *  
 *  Copyright: Marcy Santana © 2026
 *  
 *  @author Marcy Santana
 *  
 *  @version 1.00		2026-10-05 Initial Version
 */
public class LessonsLearnedTestAutomation {
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	public static void main(String args[]) {
		// Connect to the in-memory database
		// This is a miniature program that exists outside of the gui, but connects to the same
		// database that the gui system uses, so same setup must be used.
		try {
			// Connect to the database
			theDatabase.connectToDatabase();
		} catch (SQLException e) {
			
			// If the connection request fails, it usually means some other app is using it
			System.out.println("*** ERROR ***");
			System.exit(0);
		}
		
		// Create user objects that correspond to actual uses in the system.
		User admin = new User("admin", "!Password01", "", "", "", "", "", true, false, false);
		User test = new User("test", "!Password01", "", "", "", "", "", false, true, false);
		
		// create lesson objects in the way they will be used in the system.
		LessonLearned lesson = new LessonLearned(admin.getUserName(), "Test lesson", "This lesson is a test.", "");
		LessonLearned testLesson = new LessonLearned(test.getUserName(), "Test's Lesson", "This lesson belongs to test.", "");

		
		// begin tests.
		System.out.println("===== Starting Tests =====");
		
		// Add New Lessons Test
		testAdd(admin, lesson);
		testGetAllForUser(admin);
		
		// Manage Lesson Learned Test
		lesson.setDescription("This lesson is a test, and has experience info.");
		// Add Experience Information Test
		lesson.setExperienceInfo("The staff worked very hard!");
		testUpdate(admin, lesson);
		testGetAllForUser(admin);
		
		// Other Roles Add Lessons Test
		testAdd(test, testLesson);
		testGetAllForUser(test);
		
		// Other Roles Update Lessons Tests
		lesson.setDescription("This lesson is a test, but it doesn't belong to the test user.");
		testUpdate(test, lesson);
	
		// View All Lessons Test
		testGetAll();
		
		// Delete Lesson Test.
		testDelete(admin, lesson);
		testDelete(test, testLesson);
		
		testGetAllForUser(admin);
		testGetAllForUser(test);
		testGetAll();
		
		System.out.println("===== Ending Tests ======");
	}
	
	private static void testAdd(User user, LessonLearned lesson) {		
		if (theDatabase.addLesson(user, lesson)) {
			System.out.println("Successfully added new lesson!");
		} else {
			System.out.println("ERROR: Failed to add new lesson to the database!");
		}
	}
	
	private static void testDelete(User user, LessonLearned lesson) {
		if (LessonsLearnedDeleter.deleteLessonLearned(user, lesson)) {
			System.out.println("Successfully deleted lesson!");
		} else {
			System.out.println("ERROR: Failed to delete the lesson from the database!");
		}
	}
	
	private static void testUpdate(User user, LessonLearned lesson) {
		if (LessonsLearnedUpdater.updateLessonLearned(user, lesson)) {
			System.out.println("Successfully updated lesson!");
		} else {
			System.out.println("ERROR: Failed to update lesson!");
		}
	}
	
	private static void testGetAllForUser(User user) {
		List<LessonLearned> lessons = LessonsLearnedViewer.getAllLessonsLearnedForUser(user);
		System.out.println("=== " + user.getUserName() + " Lessons ===");
		for (LessonLearned lesson : lessons) {
			System.out.println("Title: " + lesson.getTitle());
			System.out.println("Description: " + lesson.getDescription());
			System.out.println("Experience Info: " + lesson.getExperienceInfo());
		}
		System.out.println("================");
	}
	
	private static void testGetAll() {
		System.out.println("****** All Lessons ******* ");
		List<LessonLearned> lessons = LessonsLearnedViewer.getAllLessonsLearned();
		for (LessonLearned lesson : lessons) {
			System.out.println("Title: " + lesson.getTitle());
			System.out.println("Description: " + lesson.getDescription());
			System.out.println("Experience Info: " + lesson.getExperienceInfo());
			System.out.println("Creator: " + lesson.getCreatorName());
			System.out.println("****************");
		}
	}
}