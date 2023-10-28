import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bullet extends ImageView {

    private double x;
    private double y;
    private boolean isPlayer;

    public Bullet(double x, double y, int height, int width, boolean isPlayer) {
        Image image;
        if (isPlayer)
            image = new Image("playerBullet.gif", 20, 20, true, true);
        else
            image = new Image("enemyBullet.png", 20, 20, true, true);
        this.setX(x);
        this.setY(y);
        this.setImage(image);
        this.x = x;
        this.y = y;
        this.isPlayer = isPlayer;
    }

    public boolean isPlayer() {
        return this.isPlayer;
    }


}
