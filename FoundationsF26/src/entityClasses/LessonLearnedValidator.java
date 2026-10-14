package entityClasses;

/*******
 * <p> Title: LessonLearnedValidator Class </p>
 *
 * <p> Description: Provides input validation for lesson learned data.
 * Invalid input produces a helpful error message. </p>
 *
 * @author Tam Bui
 */
public class LessonLearnedValidator {

    public static String validateLesson(String title, String content) {

        if (title == null || title.trim().isEmpty()) {
            return "Lesson title cannot be blank.";
        }

        if (content == null || content.trim().isEmpty()) {
            return "Lesson content cannot be blank.";
        }

        return "";
    }
}