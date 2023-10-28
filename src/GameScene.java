import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;


public class GameScene {

    public static final int SCENE_HEIGHT = 1000;
    public static final int SCENE_WIDTH = 1000;
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 900;
    private Controller controller;
    private Pane gamePane;
    private BorderPane mainPane;
    private ArrayList<EnemyComponent> enemies;
    private ArrayList<Bullet> allBullets;
    private ArrayList<Shield> allShields;
    private PlayerComponent player;
    private AnimationTimer animationTimer;
    private Timeline timeline;
    private int score;
    private Text scoreText;
    private double checkEnemyBullet;

    public GameScene(Controller controller) {
        this.controller = controller;
        allBullets = new ArrayList<>();
        enemies = new ArrayList<>();
        allShields = new ArrayList<>();
    }

    public Scene createGameScene() {
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(getGamePane());
        mainPane.setTop(getTopPane());
        mainPane.setRight(getRightPane());
        mainPane.setLeft(getLeftPane());
        mainPane.setBottom(getDownPane());
        this.mainPane = mainPane;
        setAnimationTimer();
        this.animationTimer.start();
        setTimeline();
        this.timeline.play();
        mainPane.setFocusTraversable(true);
        setKeyPressedAction();
        Scene scene = new Scene(mainPane, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add("Styles.css");
        return scene;
    }

    public int getScore() {
        return score;
    }

    private Pane getGamePane() {
        Media sound = new Media(Paths.get("Resources/gameSound.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
        });
        mediaPlayer.play();
        mediaPlayer.play();
        PlayerComponent playerShip = new PlayerComponent(40, 40, GAME_WIDTH / 2, GAME_HEIGHT - 140);
        this.player = playerShip;
        Pane gamePane = new Pane();
        this.gamePane = gamePane;
        setEnemies();
        setShields();
        gamePane.getChildren().add(playerShip);
        gamePane.setMaxSize(900, 900);
        gamePane.setBackground(new Background(new BackgroundImage(new Image("background.jpg"), null, null, null, null)));
        gamePane.requestFocus();
        return gamePane;
    }

    private Pane getTopPane() {
        Pane pane = new Pane();
        Image image = new Image("top.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(SCENE_WIDTH);
        imageView.setFitHeight(100);
        pane.getChildren().add(imageView);
        return pane;
    }

    private Pane getRightPane() {
        Pane pane = new Pane();
        Image image = new Image("right.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(800);
        pane.getChildren().add(imageView);
        return pane;
    }

    private Pane getDownPane() {
        Pane pane = new Pane();
        Image image = new Image("bottom.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(100);
        pane.getChildren().add(imageView);
        return pane;
    }

    private Pane getLeftPane() {
        Pane pane = new Pane();
        Image image = new Image("left.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(800);
        pane.getChildren().add(imageView);
        Text textField = new Text(20, 50, "Score");
        textField.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        textField.setFill(Color.WHITE);
        Rectangle rectangle = new Rectangle(20, 60, 60, 30);
        rectangle.setFill(Color.WHITE);
        Text score = new Text(40, 80, Integer.toString(this.score));
        this.scoreText = score;
        score.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        pane.getChildren().addAll(textField, rectangle, score);
        return pane;
    }

    private void increaseScore() {
        this.scoreText.setText(Integer.toString(Integer.parseInt(this.scoreText.getText()) + 1));
    }

    private void setAnimationTimer() {
        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                checkEnemyBullet += 0.01;
                if (checkEnemyBullet >= 5 && Math.random() < 0.3) {
                    checkEnemyBullet = 0;
                    enemyShoot();
                }
                if (enemies.isEmpty()) {
                    gameEnded(true);
                    return;
                }
                updateBullets();
                for (EnemyComponent enemy : enemies) {
                    if (enemy.getBoundsInParent().getMaxY() >= 670)
                        gameEnded(false);
                    else
                        enemy.transition();
                }

            }
        };
    }

    private void updateBullets() {
        ArrayList<Bullet> removingBullets = new ArrayList<>();
        for (Bullet bullet : allBullets) {
            for (Shield shield : allShields) {
                if (shield.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                    removingBullets.add(bullet);
                    allShields.remove(shield);
                    gamePane.getChildren().remove(shield);
                    break;
                }
            }
            if (bullet.isPlayer()) {
                for (EnemyComponent enemy : enemies) {
                    if (enemy.getBoundsInParent().intersects(bullet.getBoundsInParent())) {
                        gamePane.getChildren().removeAll(enemy, bullet);
                        enemies.remove(enemy);
                        removingBullets.add(bullet);
                        score += 1;
                        increaseScore();
                        break;
                    }
                }
                if (bullet.getBoundsInParent().getMinY() <= 0) {
                    removingBullets.add(bullet);
                }
            } else {
                if (bullet.getBoundsInParent().intersects(player.getBoundsInParent()))
                    gameEnded(false);
            }
        }
        allBullets.removeAll(removingBullets);
        gamePane.getChildren().removeAll(removingBullets);
        removingBullets.clear();
    }

    private void setTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            for (EnemyComponent enemy : enemies) {
                enemy.moveDown();
            }
            addEnemies();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline = timeline;
    }

    private void setKeyPressedAction() {
        mainPane.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.RIGHT))
                player.moveRight();
            else if (e.getCode().equals(KeyCode.LEFT))
                player.moveLeft();
            else if (e.getCode().equals(KeyCode.SPACE))
                shoot(player);
        });
        for (Node child : mainPane.getChildren()) {
            child.setFocusTraversable(true);
        }
    }

    private void setEnemies() {
        int x = 100;
        int y = 100;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                EnemyComponent enemy = new EnemyComponent(40, 40, x + j * 60, y + i * 40, j);
                this.enemies.add(enemy);
                gamePane.getChildren().add(enemy);
            }
            EnemyComponent.rowCreated();
        }
    }

    private void setShields() {
        for (int i = 0; i < 4; i++) {
            Shield shield = new Shield(50 + 200 * i, 670);
            gamePane.getChildren().add(shield);
            allShields.add(shield);
        }
    }

    private void addEnemies() {
        double minY = 1000;
        double minX = enemies.get(0).getMinimumRowX();
        for (EnemyComponent enemy : enemies) {
            if (enemy.getBoundsInParent().getMinY() < minY)
                minY = enemy.getBoundsInParent().getMinY();
        }
        if (minY > 100) {
            EnemyComponent.rowCreated();
            for (int j = 0; j < 10; j++) {
                EnemyComponent enemy = new EnemyComponent(40, 40, minX + j * 60, minY - 40, j);
                this.enemies.add(enemy);
                gamePane.getChildren().add(enemy);
            }
        }

    }

    private void shoot(ImageView player) {
        Media shootingSound = new Media(Paths.get("Resources/shootSound.mp3").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(shootingSound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.play();
        Bullet bullet = ((PlayerComponent) player).shoot();
        allBullets.add(bullet);
        gamePane.getChildren().add(bullet);
        PathTransition transition = new PathTransition();
        transition.setNode(bullet);
        transition.setDuration(Duration.seconds(0.75));
        transition.setPath(new Line(bullet.getX(), bullet.getY(), bullet.getX(), 0));
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
    }

    private void enemyShoot() {
        Random randomNumber = new Random();
        int randomEnemy = randomNumber.nextInt(enemies.size() - 1);
        Bullet bullet = enemies.get(randomEnemy).shoot();
        allBullets.add(bullet);
        gamePane.getChildren().add(bullet);
        PathTransition transition = new PathTransition();
        transition.setNode(bullet);
        transition.setDuration(Duration.seconds(1));
        transition.setPath(new Line(bullet.getX(), bullet.getY(), bullet.getX(), 1000));
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
    }

    private void gameEnded(boolean isWinner) {
        ImageView image = new ImageView();
        Pane pane = new Pane();
        this.timeline.stop();
        this.animationTimer.stop();
        if (!isWinner)
            image.setImage(new Image("gameover.jpg", 800, 800, false, false));
        else
            image.setImage(new Image("win.jpg", 800, 800, false, false));
        pane.getChildren().add(image);
        Button tryAgain = new Button("TRY AGAIN!");
        tryAgain.setLayoutX(550);
        tryAgain.setLayoutY(650);
        tryAgain.setOnMouseClicked(event -> controller.startNewGame());
        Button quitGame = new Button("BACK!");
        quitGame.setLayoutX(300);
        quitGame.setLayoutY(650);
        quitGame.setOnMouseClicked(event -> controller.quitGame());
        pane.getChildren().addAll(quitGame, tryAgain);
        mainPane.setCenter(pane);

    }


}
