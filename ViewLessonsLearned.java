package guiLessonsLearned;

import java.util.List;
import javafx.scene.control.ListView;
import database.Database;
import entityClasses.LessonLearned;
import entityClasses.LessonLearnedValidator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewLessonsLearned {

	public static void display(Stage stage, Database database, String username) {
		Label heading = new Label("Create a Lesson Learned");
		TextField titleField = new TextField();
		titleField.setPromptText("Lesson title");
		TextArea descriptionField = new TextArea();
		descriptionField.setPromptText("Lesson description");
		
		Label message = new Label();
		Label listHeading = new Label("My Lessons Learned");
		ListView<String> lessonList = new ListView<>();
		lessonList.setPrefHeight(120);
		List<LessonLearned> lessons = database.getLessonsByContributor(username);
		
		//populate the list with lesson titles
		for (LessonLearned lesson : lessons) {
			lessonList.getItems().add(lesson.getTitle());
		}
		
		//show details when a lesson is selected
		Label lessonDetails = new Label("Select a lesson to view details");
		lessonDetails.setWrapText(true);
		lessonList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			int index = newValue.intValue();
			if(index >= 0 && index < lessons.size()) {
				LessonLearned selected = lessons.get(index);
				lessonDetails.setText(
						"Title: " + selected.getTitle() +
						"\nDescription: " + selected.getDescription() +
						"\nContributor: " + selected.getContributor() +
						"\nLocked: " + selected.getLocked()
						);
			}
		});
		
		Button deleteButton = new Button("Delete selected Lesson");
		deleteButton.setOnAction(event -> {
			int index = lessonList.getSelectionModel().getSelectedIndex();
			if(index < 0 || index >= lessons.size()) {
				message.setText("Please select a lesson to delete.");
				return;
			}
			LessonLearned selected = lessons.get(index);
			if(selected.getLocked()) {
				message.setText("This lesson is locked and cannot be deleted.");
				return;
			}
			Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
			confirmation.setTitle("Confirm Delete");
			confirmation.setHeaderText("Delete this lesson?");
			confirmation.setContentText(selected.getTitle());
			confirmation.showAndWait().ifPresent(response -> {
				if(response == ButtonType.OK) {
					if(database.deleteLesson(selected.getLessonID(),  username)) {
						message.setText("Lesson deleted successfully");
						lessons.clear();
						lessons.addAll(database.getLessonsByContributor(username));
						lessonList.getItems().clear();
						for(LessonLearned lesson : lessons) {
							lessonList.getItems().add(lesson.getTitle());
						}
						titleField.clear();
						descriptionField.clear();
						lessonDetails.setText("Select a lesson to view details.");
					} else {
						message.setText("Unable to delete lesson.");
					}
				}
			});
		});
		
		Button updateButton = new Button("Update Selected Lesson");
		updateButton.setOnAction(event -> {
			int index = lessonList.getSelectionModel().getSelectedIndex();
			if (index < 0 || index >= lessons.size()) {
				message.setText("Please select a lesson to update");
				return;
			}
			LessonLearned selected = lessons.get(index);
			titleField.setText(selected.getTitle());
			descriptionField.setText(selected.getDescription());
			if(selected.getLocked()) {
				message.setText("This lesson is locked and cannot be updated.");
				return;
			}
			String title = titleField.getText();
			String description = descriptionField.getText();
			String error = LessonLearnedValidator.validate(title,  description);
			if(!error.isEmpty()) {
				message.setText(error);
				return;
			}
			
			//use the logged in contributors identity
			LessonLearned updated = new LessonLearned();
			updated.setLessonID(selected.getLessonID());
			updated.setTitle(title);
			updated.setDescription(description);
			updated.setContributor(username);
			
			if(database.updateLesson(updated)) {
				message.setText("Lesson updated successfully");
				
				//referesh the lessons from the database
				lessons.clear();
				lessons.addAll(database.getLessonsByContributor(username));
				
				lessonList.getItems().clear();
				for(LessonLearned lesson : lessons) {
					lessonList.getItems().add(lesson.getTitle());
				}
				lessonDetails.setText("Select a lesson to view details.");
				titleField.clear();
				descriptionField.clear();
			}else {
				message.setText("unable to update lesson. It may be locked or unavailable.");
			}
		});
		
		Button saveButton = new Button("Save Lesson");
		saveButton.setOnAction(event -> {
			String title = titleField.getText();
			String description = descriptionField.getText();
			String error = LessonLearnedValidator.validate(
					title, description
					);
			if(!error.isEmpty()) {
				message.setText(error);
				return;
			}
			LessonLearned lesson = new LessonLearned();
			lesson.setTitle(title);
			lesson.setDescription(description);
			lesson.setContributor(username);
			lesson.setLocked(false);
			
			if (database.createLesson(lesson)) {
				message.setText("Lesson saved successfully");
				titleField.clear();
				descriptionField.clear();
				//refresh the list from the data base
				lessons.clear();
				lessons.addAll(database.getLessonsByContributor(username));
				lessonList.getItems().clear();
				for(LessonLearned savedLesson : lessons) {
					lessonList.getItems().add(savedLesson.getTitle());
				}
				lessonDetails.setText("Select a lesson to view details.");
			}else {
				message.setText("Unable to save lesson");
			}
		});
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(
				heading, titleField, descriptionField, saveButton, updateButton, deleteButton, message, listHeading, lessonList, lessonDetails);
		stage.setScene(new Scene(layout, 600, 600));
		stage.show();
	}
	
}
