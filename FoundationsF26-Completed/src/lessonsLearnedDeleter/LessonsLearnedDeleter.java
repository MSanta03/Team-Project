package lessonsLearnedDeleter;

import database.Database;
import entityClasses.LessonLearned;
import entityClasses.User;

/**
 * Title: LessonsLearnedDeleter
 * 
 *  Description: Exposes database functions to delete specific lessons from the database.
 *  
 *  Copyright: Marcy Santana © 2026
 *  
 *  @author Marcy Santana
 *  
 *  @version 1.00		2026-10-05 Initial Version
 */
public class LessonsLearnedDeleter {
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**
	 * Method: boolean deleteLessonLearned(User user, LessonLearned lesson)
	 * 
	 * Description: Attempts to delete the given lesson from the database and alerts the caller if the 
	 * delete operation was successful.
	 * 
	 * @param user The creator of the lesson
	 * @param lesson The lesson to be deleted
	 * @return Whether or not the lesson was successfully deleted.
	 */
	public static boolean deleteLessonLearned(User user, LessonLearned lesson) {
		return theDatabase.deleteLesson(user, lesson);
	}
}