package lessonsLearnedUpdater;

import database.Database;
import entityClasses.LessonLearned;
import entityClasses.User;

/**
 * Title: LessonsLearnedUpdater
 * 
 *  Description: Exposes database functions to update specific lessons in the database.
 *  
 *  Copyright: Marcy Santana © 2026
 *  
 *  @author Marcy Santana
 *  
 *  @version 1.00		2026-10-05 Initial Version
 */
public class LessonsLearnedUpdater {
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**
	 * Method: boolean updateLessonLearned(User user, LessonLearned lesson) 
	 * 
	 * Description: Attempts to update the given lesson with new information and alerts the caller of the operation
	 * was successful.
	 * 
	 * @param user The creator of the lesson
	 * @param lesson The lesson with new information to be updated
	 * @return Whether or not the update operation was successful.
	 * */
	public static boolean updateLessonLearned(User user, LessonLearned lesson) {
		return theDatabase.updateLesson(user, lesson);
	}
}