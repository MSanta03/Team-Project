package entityClasses;

/*******
 * <p> Title: LessonLearned Class </p>
 *
 * <p> Description: This class represents a lesson learned entity.
 * It stores the lesson ID, title, and content. </p>
 *
 * @author Tam Bui
 */
public class LessonLearned {

    private int id;
    private String title;
    private String content;

    public LessonLearned() {
    }

    public LessonLearned(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public LessonLearned(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}