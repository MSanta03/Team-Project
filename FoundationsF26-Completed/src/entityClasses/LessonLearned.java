package entityClasses;

/**
 * <p> Title: LessonLearned <p>
 * 
 * <p> Description: The class representation of the entity class for lessons learned. The
 * class stores the title, description, experience information, and creator name for a given lesson.
 * This object is used for simple manipulation throughout the system before it is submitted to the database. </p>
 * 
 * <p> Copyright: Marcy Santana © 2026 </p>
 * 
 * @author Marcy Santana
 * 
 * @version 1.00	2026-10-05 Initial Version
 * 
 * */
public class LessonLearned {
	private String title;
	private String description;
	private String experienceInfo;
	private String creatorName;
	
	/**
	 * Method: LessonLearned(String creator)
	 * 
	 * Description: Instantiates the object and sets the creator name
	 * 
	 * @param creator the name of the creator of the lesson
	 * */
	public LessonLearned(String creator) {
		this.creatorName = creator;
	}
	
	/**
	 * Method: LessonLearned(String creator, String title, String description, String experienceInfo)
	 * 
	 * Description: Instantiates the object and sets all of its information
	 * 
	 * @param creator The name of the creator of the lesson.
	 * @param title The title of the lesson
	 * @param description The description of the lesson.
	 * @param experienceInfo The experience information for the lesson.
	 * 
	 * */
	public LessonLearned(String creator, String title, String description, String experienceInfo) {
		this.creatorName = creator;
		this.title = title;
		this.description = description;
		this.experienceInfo = experienceInfo;
	}
	
	/**
	 * Method: setTitle(String title)
	 * 
	 * Description: Sets the title of the lesson.
	 * 
	 * @param title The new title of the lesson.
	 * 
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Method: String getTitle()
	 * 
	 * Description: Returns the title of the lesson
	 * 
	 * @return The title of the lesson.
	 * */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Method: void setDescription(String description)
	 * 
	 * Description: Sets the new description of the lesson.
	 * 
	 * @param description The new description of the lesson.
	 * */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method: String getDescription()
	 * 
	 * Description: Returns the description of the lesson.
	 * 
	 * @return The description of the lesson.
	 * 
	 * */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Method: void setExperienceInfo(String info)
	 * 
	 *  Description: Sets the new experience information for the lesson.
	 *  
	 *  @param info the new experience information for the lesson.
	 */
	public void setExperienceInfo(String info) {
		this.experienceInfo = info;
	}
	
	
	/**
	 * Method: String getExperienceInfo()
	 * 
	 * Description: Returns the experience information for the lesson.
	 * 
	 * @return the experience information for the lesson.
	 * 
	 * */
	public String getExperienceInfo() {
		return experienceInfo;
	}
	
	/**
	 * Method: String getCreatorName()
	 * 
	 *  Description: returns the name of the creator of the lesson.
	 *  
	 *  @return the name of the creator of the lesson.
	 */
	public String getCreatorName() {
		return creatorName;
	}
}