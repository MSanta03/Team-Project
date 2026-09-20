package emailAddressTestbed;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import validation.EmailValidator;
import validation.UserNameRecognizer;

/**
 * GUI demonstration for Tam's assigned TP1 validation tasks:
 * TP1-12 Username Recognizer
 * TP1-14 Maximum-size validation
 * TP1-15 Improved email-address validation using the professor's FSM
 */
public class UserInterface {

    private final Label title = new Label("TP1 Input Validation Demo");

    private final Label usernameLabel = new Label("Username");
    private final TextField usernameField = new TextField();
    private final Label usernameResult = new Label("");

    private final Label emailLabel = new Label("Email Address");
    private final TextField emailField = new TextField();
    private final Label emailResult = new Label("");

    private final Button validateButton = new Button("Validate Inputs");
    private final Label overallResult = new Label("");

    public UserInterface(Pane root) {
        setupLabel(title, 26, 620, Pos.CENTER, 10, 15);

        setupLabel(usernameLabel, 15, 150, Pos.BASELINE_LEFT, 25, 75);
        setupTextField(usernameField, 18, 590, 25, 100);
        setupLabel(usernameResult, 14, 590, Pos.BASELINE_LEFT, 25, 140);

        setupLabel(emailLabel, 15, 150, Pos.BASELINE_LEFT, 25, 185);
        setupTextField(emailField, 18, 590, 25, 210);
        setupLabel(emailResult, 14, 590, Pos.BASELINE_LEFT, 25, 250);

        validateButton.setFont(Font.font("Arial", 17));
        validateButton.setLayoutX(235);
        validateButton.setLayoutY(295);
        validateButton.setPrefWidth(170);
        validateButton.setOnAction(event -> validateInputs());

        setupLabel(overallResult, 15, 590, Pos.CENTER, 25, 345);

        usernameField.textProperty().addListener((obs, oldValue, newValue) -> clearResults());
        emailField.textProperty().addListener((obs, oldValue, newValue) -> clearResults());

        root.getChildren().addAll(
                title,
                usernameLabel, usernameField, usernameResult,
                emailLabel, emailField, emailResult,
                validateButton, overallResult);
    }

    private void validateInputs() {
        String usernameError = UserNameRecognizer.validate(usernameField.getText());
        String emailError = EmailValidator.validate(emailField.getText());

        if (usernameError.isEmpty()) {
            usernameResult.setText("✓ Username is valid.");
            usernameResult.setTextFill(Color.GREEN);
        } else {
            usernameResult.setText("✗ " + usernameError);
            usernameResult.setTextFill(Color.RED);
        }

        if (emailError.isEmpty()) {
            emailResult.setText("✓ Email address is valid.");
            emailResult.setTextFill(Color.GREEN);
        } else {
            String oneLineError = emailError.replace('\n', ' ').trim();
            emailResult.setText("✗ " + oneLineError);
            emailResult.setTextFill(Color.RED);
        }

        if (usernameError.isEmpty() && emailError.isEmpty()) {
            overallResult.setText("All TP1 validation checks passed.");
            overallResult.setTextFill(Color.GREEN);
        } else {
            overallResult.setText("Please correct the highlighted validation error(s).");
            overallResult.setTextFill(Color.RED);
        }
    }

    private void clearResults() {
        usernameResult.setText("");
        emailResult.setText("");
        overallResult.setText("");
    }

    private void setupLabel(Label label, double fontSize, double width,
            Pos alignment, double x, double y) {
        label.setFont(Font.font("Arial", fontSize));
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setAlignment(alignment);
        label.setLayoutX(x);
        label.setLayoutY(y);
    }

    private void setupTextField(TextField field, double fontSize,
            double width, double x, double y) {
        field.setFont(Font.font("Arial", fontSize));
        field.setPrefWidth(width);
        field.setLayoutX(x);
        field.setLayoutY(y);
    }
}
