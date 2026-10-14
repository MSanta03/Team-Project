package entityClasses;

import java.util.ArrayList;

/**
 * <p>Title: LessonList Class.</p>
 *
 * <p>Description: The LessonList class stores a collection of Lesson objects.
 * It can contain all lessons or a subset of lessons.</p>
 *
 * @author Ethan
 *
 * @version 1.00 2026-10-05 Initial version for HW2
 */
public class LessonList {
	private ArrayList<Lesson> lessons;

	/** Constructs an empty LessonList. */
	public LessonList() {
		lessons = new ArrayList<Lesson>();
	}

	/**
	 * Adds a lesson to the list.
	 *
	 * @param lesson the Lesson to add
	 */
	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}

	/**
	 * Gets the lessons in this list.
	 *
	 * @return the collection of lessons
	 */
	public ArrayList<Lesson> getLessons() {
		return lessons;
	}
}
