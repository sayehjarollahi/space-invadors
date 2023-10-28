
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class PlayerComponent extends ImageView {

    int width;
    int height;

    public PlayerComponent(int height, int width, int x, int y) {
        Image image = new Image("player.png", width, height, true, true);
        this.setImage(image);
        this.setX(x);
        this.setY(y);
        this.height = height;
        this.width = width;
    }

    public void moveRight() {
        if (this.getTranslateX() + width < GameScene.GAME_WIDTH / 2)
            this.setTranslateX(this.getTranslateX() + 5);
    }

    public void moveLeft() {
        if (this.getTranslateX() - 5 > -GameScene.GAME_WIDTH / 2)
            this.setTranslateX(this.getTranslateX() - 5);
    }

    public Bullet shoot() {
        return new Bullet(this.getBoundsInParent().getMaxX() - (width / 2), this.getBoundsInParent().getMinY(), 10, 2, true);
    }


}
