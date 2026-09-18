package guiListUsers;

import java.util.List;

import database.Database;
import entityClasses.User;
import guiAdminHome.ViewAdminHome;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewListUsers {
	
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	
	public static void displayListUsers(Stage stage, User currentUser) {
		Pane root = new Pane();
		
		Label title = new Label("List All Users");
		title.setFont(Font.font("Arial", 28));
		title.setLayoutX(20);
		title.setLayoutY(20);
		
		//column headings
		Label usernameHeading = new Label("Username");
		Label nameHeading = new Label("Name");
		Label emailHeading = new Label("Email");
		Label rolesHeading = new Label("Roles");
		
		usernameHeading.setFont(Font.font("Arial", 16));
		nameHeading.setFont(Font.font("Arial", 16));
		emailHeading.setFont(Font.font("Arial", 16));
		rolesHeading.setFont(Font.font("Arial", 16));
	
		usernameHeading.setLayoutX(30);
		nameHeading.setLayoutX(150);
		emailHeading.setLayoutX(290);
		rolesHeading.setLayoutX(445);
		
		usernameHeading.setLayoutY(70);
		nameHeading.setLayoutY(70);
		emailHeading.setLayoutY(70);
		rolesHeading.setLayoutY(70);
		
		Pane usersPane = new Pane();
		
		List<User> users = theDatabase.getAllUsers();
		
		double y = 10;
		
		for (User user : users) {
			String name = user.getFirstName() + " " + user.getLastName();
			String roles = "";
			
			if(user.getAdminRole()) {
				roles += "Admin ";
			}
			
			if(user.getNewRole1()) {
				if(!roles.isEmpty()) {
					roles += ", ";
				}
				roles += "Role1 ";
			}
			
			if(user.getNewRole2()) {
				if(!roles.isEmpty()){
					roles += ", ";
				}
				roles += "Role2 ";
			}
			if(roles.isEmpty()) {
				roles = "None";
			}
			Label usernameLabel = new Label(user.getUserName());
			Label nameLabel = new Label(name);
			Label emailLabel = new Label(user.getEmailAddress());
			Label rolesLabel = new Label(roles);
			
			usernameLabel.setFont(Font.font("Arial", 14));
			nameLabel.setFont(Font.font("Arial", 14));
			emailLabel.setFont(Font.font("Arial",14));
			rolesLabel.setFont(Font.font("Arial", 14));
			
			usernameLabel.setLayoutX(10);
			nameLabel.setLayoutX(130);
			emailLabel.setLayoutX(270);
			rolesLabel.setLayoutX(425);
			
			usernameLabel.setLayoutY(y);
			nameLabel.setLayoutY(y);
			emailLabel.setLayoutY(y);
			rolesLabel.setLayoutY(y);
			
			usersPane.getChildren().addAll(usernameLabel, nameLabel, emailLabel, rolesLabel);
			y += 30;
		}
		usersPane.setPrefHeight(y + 20);
		usersPane.setPrefWidth(760);
		
		ScrollPane scrollPane = new ScrollPane(usersPane);
		scrollPane.setLayoutX(20);
		scrollPane.setLayoutY(100);
		scrollPane.setPrefWidth(760);
		scrollPane.setPrefHeight(380);
		
		Button returnButton = new Button("Return to Admin Home");
		returnButton.setFont(Font.font("Dialog", 16));
		returnButton.setLayoutX(20);
		returnButton.setLayoutY(510);
		
		returnButton.setOnAction(e -> ViewAdminHome.displayAdminHome(stage, currentUser));
		
		root.getChildren().addAll(title, usernameHeading, nameHeading, emailHeading, rolesHeading, scrollPane, returnButton);
		
		Scene scene = new Scene(root, applicationMain.FoundationsMain.WINDOW_WIDTH, applicationMain.FoundationsMain.WINDOW_HEIGHT);
		
		stage.setTitle("CSE 360 Foundation Code: List All Users");
		stage.setScene(scene);
		stage.show();
		
	}

}
