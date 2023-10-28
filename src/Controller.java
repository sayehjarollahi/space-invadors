import Exceptions.NoUserNameException;
import Exceptions.UsernameExistException;
import Exceptions.WrongPasswordException;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Controller {

    private Stage windows;
    private EntryScene entryScene;
    private AccountsMenu accountsMenu;
    private GameScene sceneBuilder;
    private Player player;
    private Button startGame;
    private Text usernameEditingText;
    private TextField usernameEditing;
    private TextField passwordField;
    private Text passwordText;
    private TextField usernameLogin;
    private PasswordField passwordLogin;
    private Text loginText;
    private TextField usernameRegister;
    private PasswordField passwordRegister;
    private Text registerText;


    public Controller(Stage windows) {
        this.windows = windows;
        this.entryScene = new EntryScene(this);
        this.accountsMenu = new AccountsMenu(this);
        windows.setScene(entryScene.getMenuScene());
        windows.show();
    }

    public void startNewGame() {
        if (sceneBuilder != null)
            player.setScore(sceneBuilder.getScore());
        this.sceneBuilder = new GameScene(this);
        windows.setScene(sceneBuilder.createGameScene());
    }

    public void quitGame() {
        player.setScore(sceneBuilder.getScore());
        this.sceneBuilder = null;
        windows.setScene(entryScene.getMenuScene());
    }

    public void setStartButton(Button button) {
        this.startGame = button;
    }

    public void openAccountsMenu() {
        if (player == null)
            windows.setScene(accountsMenu.getLoginScene());
        else
            windows.setScene(accountsMenu.getUserScene());
    }

    public void openLeaderShipsMenu() {
        windows.setScene(accountsMenu.getLeaderShipScene());
    }

    public void exit() {
        windows.close();
    }

    public void setUserNameEditing(Text text, TextField textField) {
        this.usernameEditingText = text;
        this.usernameEditing = textField;
    }

    public void setLoginFields(TextField userNameLogin, PasswordField passwordLogin, Text loginText) {
        this.usernameLogin = userNameLogin;
        this.passwordLogin = passwordLogin;
        this.loginText = loginText;
    }

    public void setRegisterFields(TextField userNameRegister, PasswordField passwordRegister, Text registerText) {
        this.usernameRegister = userNameRegister;
        this.passwordRegister = passwordRegister;
        this.registerText = registerText;
    }

    public void editUsername() {
        if (usernameEditing.getText().isEmpty()) {
            usernameEditingText.setText("You have to type username first!");
            usernameEditingText.setFill(Color.RED);
        } else if (usernameEditing.getText().contains(" ")) {
            usernameEditingText.setText("Username cannot have space!");
            usernameEditingText.setFill(Color.RED);
        } else {
            try {
                this.player.setUsername(usernameEditing.getText());
            } catch (UsernameExistException e) {
                usernameEditingText.setText("Username exists!");
                usernameEditingText.setFill(Color.RED);
                return;
            }
            usernameEditingText.setText("Successfully changed!");
            usernameEditingText.setFill(Color.GREEN);
        }
    }

    public void setPasswordField(TextField passwordField, Text passwordText) {
        this.passwordField = passwordField;
        this.passwordText = passwordText;
    }

    public void editPassword() {
        if (passwordField.getText().isEmpty()) {
            passwordText.setText("Enter password first!");
            passwordText.setFill(Color.RED);
            return;
        }
        player.setPassword(passwordField.getText());
        passwordText.setText("Changed successfully");
        passwordText.setFill(Color.GREEN);

    }

    public void logout() {
        this.player = null;
        startGame.setDisable(true);
        windows.setScene(entryScene.getMenuScene());
    }

    public void login() {
        this.loginText.setFill(Color.RED);
        if (usernameLogin.getText().isEmpty() || passwordLogin.getText().isEmpty())
            this.loginText.setText("Complete fields!");
        else {
            try {
                this.player = Player.login(usernameLogin.getText(), passwordLogin.getText());
                this.startGame.setDisable(false);
                backToMain();
            } catch (NoUserNameException e) {
                this.loginText.setText("Invalid username!");
            } catch (WrongPasswordException e) {
                this.loginText.setText("Wrong password!");
            }
        }
    }

    public void register() {
        if (this.usernameRegister.getText().isEmpty() || this.passwordRegister.getText().isEmpty()) {
            this.registerText.setText("Complete all fields");
            return;
        }
        try {
            Player.createAccount(usernameRegister.getText(), passwordRegister.getText());
            this.registerText.setText("successfully registered!");
            this.registerText.setFill(Color.GREEN);
        } catch (UsernameExistException e) {
            this.registerText.setText("Username already exists!");
            this.registerText.setFill(Color.RED);
        }
    }

    public void backToMain() {
        windows.setScene(entryScene.getMenuScene());
    }


}
