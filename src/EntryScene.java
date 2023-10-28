import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class EntryScene {
    private VBox vbox;
    private Controller controller;
    private Scene scene;

    public EntryScene(Controller controller) {
        this.controller = controller;
    }

    public Scene getMenuScene() {
        if (this.scene == null)
            createScene();
        return this.scene;
    }

    private void createScene() {
        VBox vbox = new VBox();
        this.vbox = vbox;
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        setButtons();
        this.scene = new Scene(vbox, GameScene.SCENE_WIDTH, GameScene.SCENE_HEIGHT);
        this.scene.getStylesheets().add("MenuStyle.css");
    }

    private void setButtons() {
        Button button = new Button("Play Game");
        controller.setStartButton(button);
        button.setDisable(true);
        button.setOnMouseClicked(event -> controller.startNewGame());
        vbox.getChildren().add(button);

        button = new Button("Accounts");
        button.setOnMouseClicked(event -> controller.openAccountsMenu());
        vbox.getChildren().add(button);

        button = new Button("LeaderShip");
        button.setOnMouseClicked(event -> controller.openLeaderShipsMenu());
        vbox.getChildren().add(button);

        button = new Button("Exit");
        button.setOnMouseClicked(event -> controller.exit());
        vbox.getChildren().add(button);

    }
}
