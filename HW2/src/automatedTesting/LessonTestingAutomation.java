package automatedTesting;

import java.sql.SQLException;

import database.Database;
import entityClasses.Lesson;
import entityClasses.LessonList;
import lessonValidator.LessonValidator;

/*******
 * <p> Title: LessonTestingAutomation Class. </p>
 *
 * <p> Description: Automated tests for the HW2 lesson CRUD and validation. </p>
 *
 * @author Ethan
 *
 * @version 1.00 2026-10-07 Initial version for HW2
 */
public class LessonTestingAutomation {

	static int numPassed = 0;
	static int numFailed = 0;
	static Database database = new Database();

	static String testUser = "HW2TestUser";
	static String otherUser = "HW2OtherUser";

	public static void main(String[] args) throws SQLException {
		System.out.println("______________________________________");
		System.out.println("\nLesson Testing Automation");

		database.connectToDatabase();
		clearTestLessons(testUser);
		clearTestLessons(otherUser);

		// Test 1: Create a valid lesson and verify persistence after reconnecting
		Lesson lesson = new Lesson(0, testUser, "Use Git Often", "Commit small changes while working.");
		database.createLesson(lesson);
		database.closeConnection();
		database.connectToDatabase();
		LessonList lessons = database.getLessonsByContributor(testUser);
		performTestCase(1, lessons.getLessons().size() == 1, "Create lesson and persistence");

		// Test 2: Invalid lesson input is rejected by validation
		performTestCase(2,
				!LessonValidator.validateTitle("").isEmpty() &&
				!LessonValidator.validateDescription("").isEmpty(),
				"Reject incomplete lesson input");

		// Test 3: An empty lesson list is supported
		performTestCase(3, database.getLessonsByContributor(otherUser).getLessons().size() == 0,
				"Read an empty lesson list");

		// Test 4: A list containing one lesson is supported
		performTestCase(4, database.getLessonsByContributor(testUser).getLessons().size() == 1,
				"Read one lesson");

		// Test 5: A list containing many lessons is supported
		database.createLesson(new Lesson(0, testUser, "Ask Questions", "Ask for help when something is unclear."));
		database.createLesson(new Lesson(0, testUser, "Test Changes", "Run tests after making a change."));
		performTestCase(5, database.getLessonsByContributor(testUser).getLessons().size() == 3,
				"Read many lessons");

		// Test 6: Contributor queries return only that contributor's lessons
		database.createLesson(new Lesson(0, otherUser, "Other Lesson", "This belongs to another contributor."));
		lessons = database.getLessonsByContributor(testUser);
		boolean correctSubset = lessons.getLessons().size() == 3;
		for (Lesson currentLesson : lessons.getLessons()) {
			if (!currentLesson.getContributorUserName().equals(testUser)) correctSubset = false;
		}
		performTestCase(6, correctSubset, "Read a contributor subset");

		// Test 7: Update a lesson and verify persistence after reconnecting
		Lesson selectedLesson = lessons.getLessons().get(0);
		Lesson updatedLesson = new Lesson(selectedLesson.getLessonID(), testUser,
				"Updated Lesson", "This lesson has been updated.");
		boolean updated = database.updateLesson(updatedLesson);
		database.closeConnection();
		database.connectToDatabase();
		lessons = database.getLessonsByContributor(testUser);
		boolean updateFound = false;
		for (Lesson currentLesson : lessons.getLessons()) {
			if (currentLesson.getLessonID() == selectedLesson.getLessonID() &&
					currentLesson.getTitle().equals("Updated Lesson")) updateFound = true;
		}
		performTestCase(7, updated && updateFound, "Update lesson and persistence");

		// Test 8: Invalid update input is caught before a database update
		String oldTitle = lessons.getLessons().get(0).getTitle();
		String errorMessage = LessonValidator.validateTitle("   ");
		performTestCase(8, !errorMessage.isEmpty() && oldTitle.equals(lessons.getLessons().get(0).getTitle()),
				"Reject invalid update input");

		// Test 9: Delete a lesson and verify persistence after reconnecting
		int deleteID = lessons.getLessons().get(0).getLessonID();
		boolean deleted = database.deleteLesson(deleteID, testUser);
		database.closeConnection();
		database.connectToDatabase();
		lessons = database.getLessonsByContributor(testUser);
		boolean deleteFound = false;
		for (Lesson currentLesson : lessons.getLessons()) {
			if (currentLesson.getLessonID() == deleteID) deleteFound = true;
		}
		performTestCase(9, deleted && !deleteFound, "Delete lesson and persistence");

		// Test 10: Deleting an ID that does not exist returns false
		int sizeBeforeDelete = lessons.getLessons().size();
		boolean missingDelete = database.deleteLesson(-1, testUser);
		int sizeAfterDelete = database.getLessonsByContributor(testUser).getLessons().size();
		performTestCase(10, !missingDelete && sizeBeforeDelete == sizeAfterDelete,
				"Delete a lesson that does not exist");

		// Test 11: Required fields reject empty and whitespace input
		boolean requiredFields = !LessonValidator.validateTitle("").isEmpty()
				&& !LessonValidator.validateTitle("   ").isEmpty()
				&& !LessonValidator.validateDescription("").isEmpty()
				&& !LessonValidator.validateDescription("   ").isEmpty();
		performTestCase(11, requiredFields, "Validate required lesson fields");

		clearTestLessons(testUser);
		clearTestLessons(otherUser);
		database.closeConnection();

		System.out.println("____________________________________________________________________________");
		System.out.println();
		System.out.println("Number of tests passed: " + numPassed);
		System.out.println("Number of tests failed: " + numFailed);
	}

	private static void performTestCase(int testCase, boolean passed, String description) {
		System.out.println("____________________________________________________________________________");
		System.out.println("\nTest case: " + testCase);
		System.out.println(description);

		if (passed) {
			System.out.println("***Success*** This test passed.");
			numPassed++;
		}
		else {
			System.out.println("***Failure*** This test failed.");
			numFailed++;
		}
	}

	private static void clearTestLessons(String userName) throws SQLException {
		LessonList lessons = database.getLessonsByContributor(userName);
		for (Lesson lesson : lessons.getLessons()) {
			database.deleteLesson(lesson.getLessonID(), userName);
		}
	}
}
