package entityClasses;

/**
 * <p>Title: Lesson Class.</p>
 *
 * <p>Description: The Lesson class represents one lesson learned stored by
 * the application.</p>
 *
 * @author Ethan
 *
 * @version 1.00 2026-10-05 Initial version for HW2
 */
public class Lesson {
	private int lessonID;
	private String contributorUserName;
	private String title;
	private String description;

	/**
	 * Constructs a Lesson using the stored lesson information.
	 *
	 * @param lessonID the database identifier for the lesson
	 * @param contributorUserName the user name of the contributor
	 * @param title the lesson title
	 * @param description the lesson description
	 */
	public Lesson(int lessonID, String contributorUserName, String title, String description) {
		this.lessonID = lessonID;
		this.contributorUserName = contributorUserName;
		this.title = title;
		this.description = description;
	}

	/** @return the lesson ID */
	public int getLessonID() { return lessonID; }

	/** @return the contributor user name */
	public String getContributorUserName() { return contributorUserName; }

	/** @return the lesson title */
	public String getTitle() { return title; }

	/** @return the lesson description */
	public String getDescription() { return description; }
}
