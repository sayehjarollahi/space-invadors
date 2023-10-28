import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class AccountsMenu {
    Controller controller;

    public AccountsMenu(Controller controller) {
        this.controller = controller;
    }

    public Scene getUserScene() {
        VBox vBox = new VBox();
        TextField textField = new TextField();
        textField.setPromptText("New username");
        Text text = new Text();
        Button button = new Button("edit username");
        controller.setUserNameEditing(text, textField);
        button.setOnMouseClicked(event -> controller.editUsername());
        vBox.getChildren().addAll(text, textField, button);

        textField = new TextField();
        text = new Text();
        controller.setPasswordField(textField, text);
        button = new Button("edit password");
        button.setOnMouseClicked(event -> controller.editPassword());
        textField.setPromptText("new password");
        vBox.getChildren().addAll(textField, button, text);

        button = new Button("Logout!");
        button.setId("logout");
        vBox.getChildren().add(button);
        button.setOnMouseClicked(event -> controller.logout());

        button = new Button("back");
        button.setId("back");
        vBox.getChildren().add(button);
        button.setOnMouseClicked(event -> controller.backToMain());

        Scene scene = new Scene(vBox, GameScene.SCENE_WIDTH, GameScene.SCENE_HEIGHT);
        scene.getStylesheets().add("Styles.css");
        return scene;
    }

    public Scene getLoginScene() {
        VBox vBox = new VBox();
        Text text = new Text("Login");
        text.setId("type");
        vBox.getChildren().add(text);
        TextField textField = new TextField();
        textField.setPromptText("UserName");
        PasswordField password = new PasswordField();
        text = new Text();
        password.setPromptText("Password");
        controller.setLoginFields(textField, password, text);
        Button button = new Button("Login");
        button.setOnMouseClicked(event -> controller.login());
        vBox.getChildren().addAll(textField, password, text, button);

        Text registerText = new Text("Don't have account yet?");
        registerText.setId("type");
        vBox.getChildren().add(registerText);
        textField = new TextField();
        textField.setPromptText("UserName");
        password = new PasswordField();
        text = new Text();
        password.setPromptText("Password");
        controller.setRegisterFields(textField, password, text);
        button = new Button("register!");
        button.setOnMouseClicked(event -> controller.register());
        vBox.getChildren().addAll(textField, password, text, button);

        button = new Button("back");
        button.setId("back");
        button.setOnMouseClicked(event -> controller.backToMain());
        vBox.getChildren().addAll(button);
        Scene scene = new Scene(vBox, 1000, 1000);
        scene.getStylesheets().add("Styles.css");
        return scene;
    }

    public Scene getLeaderShipScene() {
        VBox vBox = new VBox();
        vBox.setId("leadershipBox");
        vBox.setAlignment(Pos.CENTER);
        TableView<Player> tableView = new TableView();

        TableColumn<Player, Integer> rank = new TableColumn<>("rank");
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rank.setMinWidth(350);
        rank.setSortable(false);
        TableColumn<Player, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        username.setMinWidth(350);
        username.setSortable(false);
        TableColumn<Player, Integer> highestScore = new TableColumn<>("highest score");
        highestScore.setCellValueFactory(new PropertyValueFactory<>("highestScore"));
        highestScore.setMinWidth(350);
        highestScore.setSortable(false);
        tableView.setItems(Player.getAllPlayersSorted());
        tableView.getColumns().addAll(rank, username, highestScore);
        tableView.setMaxHeight(GameScene.SCENE_HEIGHT);
        vBox.getChildren().add(tableView);
        Button backButton = new Button("back");
        backButton.setId("back");
        backButton.setOnMouseClicked(event -> controller.backToMain());
        vBox.getChildren().add(backButton);
        Scene scene = new Scene(vBox, 1000, 1000);
        scene.getStylesheets().add("Styles.css");
        return scene;
    }
}
