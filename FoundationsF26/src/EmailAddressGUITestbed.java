package emailAddressTestbed;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX GUI used to demonstrate Tam's TP1 validation tasks.
 */
public class EmailAddressGUITestbed extends Application {

    public static final double WINDOW_WIDTH = 640;
    public static final double WINDOW_HEIGHT = 390;

    public UserInterface theGUI;

    @Override
    public void start(Stage theStage) {
        theStage.setTitle("TP1 Input Validation Demo");

        Pane theRoot = new Pane();
        theGUI = new UserInterface(theRoot);

        Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
        theStage.setScene(theScene);
        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
