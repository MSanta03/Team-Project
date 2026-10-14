package lessonValidator;

/**
 * <p>Title: LessonValidator Class.</p>
 *
 * <p>Description: The LessonValidator class checks lesson input before it is
 * stored in the database.</p>
 *
 * @author Ethan
 *
 * @version 1.00 2026-10-05 Initial version for HW2
 */
public class LessonValidator {

	/**
	 * Validates a lesson title.
	 *
	 * @param title the title to validate
	 * @return an error message, or an empty String when valid
	 */
	public static String validateTitle(String title) {
		if (title == null || title.trim().isEmpty()) return "Title is required.";
		if (title.length() > 255) return "Title must be 255 characters or fewer.";
		return "";
	}

	/**
	 * Validates a lesson description.
	 *
	 * @param description the description to validate
	 * @return an error message, or an empty String when valid
	 */
	public static String validateDescription(String description) {
		if (description == null || description.trim().isEmpty()) return "Description is required.";
		if (description.length() > 255) return "Description must be 255 characters or fewer.";
		return "";
	}
}
