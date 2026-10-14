package entityClasses;

import java.util.ArrayList;
import java.util.List;

/*******
 * <p> Title: LessonLearnedList Class </p>
 *
 * <p> Description: Stores a collection of LessonLearned objects.
 * The list can represent all lessons or any subset of lessons,
 * including an empty subset. </p>
 *
 * @author Tam Bui
 */
public class LessonLearnedList {

    private List<LessonLearned> lessons;

    public LessonLearnedList() {
        lessons = new ArrayList<>();
    }

    public LessonLearnedList(List<LessonLearned> lessons) {
        this.lessons = new ArrayList<>(lessons);
    }

    public void addLesson(LessonLearned lesson) {
        lessons.add(lesson);
    }

    public List<LessonLearned> getLessons() {
        return lessons;
    }

    public int size() {
        return lessons.size();
    }

    public boolean isEmpty() {
        return lessons.isEmpty();
    }
}