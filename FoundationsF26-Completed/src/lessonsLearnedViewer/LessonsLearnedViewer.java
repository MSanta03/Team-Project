package lessonsLearnedViewer;

import database.Database;
import entityClasses.LessonLearned;
import entityClasses.User;
import java.util.List;

/**
 * Title: LessonsLearnedViewer
 * 
 *  Description: Exposes database functions to view different subsets of the lessons learned currently in the databasae.
 *  
 *  Copyright: Marcy Santana © 2026
 *  
 *  @author Marcy Santana
 *  
 *  @version 1.00		2026-10-05 Initial Version
 */
public class LessonsLearnedViewer {
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	/**
	 * Method: List<LessonsLearned> getALlLessonLearned()
	 * 
	 * Description: returns all the lessons currently stored in the database.
	 * 
	 * @return The Lists of all lessons currently in the database.
	 **/
	public static List<LessonLearned> getAllLessonsLearned() {
		return theDatabase.getAllLessonsLearned();
	}
	
	/**
	 * Method: List<LessonLearned> getAllLessonsLearnedForUser(User user)
	 *
	 * Description: Returns all the lesson currently in the database that belong to a specific user.
	 * 
	 * @return The list of all lessons currently in the database that belong to a specific user.
	 * */
	public static List<LessonLearned> getAllLessonsLearnedForUser(User user) {
		return theDatabase.getAllLessonsLearnedForUser(user);
	}
}