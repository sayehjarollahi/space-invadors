import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.Controller

public class SpaceInvaders extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
