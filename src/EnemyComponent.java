import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyComponent extends ImageView {
    private static boolean movingRight;
    private int numberInRow;
    private int height;
    private int width;
    private static int countInEachRow = 10;
    private static int countOfRowsCreated;

    public EnemyComponent(int height, int width, double x, double y, int numberInRow) {
        Image image;
        if (countOfRowsCreated % 2 == 0)
            image = new Image("alien1.png", width, height, true, true);
        else
            image = new Image("alien2.png", width, height, true, true);
        this.setImage(image);
        this.setX(x);
        this.setY(y);
        movingRight = true;
        this.numberInRow = numberInRow + 1;
        this.width = width;
        this.height = height;
    }

    public void moveDown() {
        this.setTranslateY(this.getTranslateY() + 10);
    }

    public static void rowCreated() {
        countOfRowsCreated += 1;
    }

    public double getMinimumRowX() {
        return this.getBoundsInParent().getMinX() - (numberInRow - 1) * width;
    }


    public void transition() {
        if (this.getBoundsInParent().getMaxX() <= numberInRow * width) {
            movingRight = true;
        } else if (this.getBoundsInParent().getMaxX() >= (GameScene.GAME_WIDTH - (countInEachRow - numberInRow + 2) * width)) {
            movingRight = false;
        }
        if (movingRight) {
            this.setTranslateX(this.getTranslateX() + 2);
        } else
            this.setTranslateX(this.getTranslateX() - 2);
    }

    public Bullet shoot() {
        return new Bullet(this.getBoundsInParent().getMaxX() - width / 2, this.getBoundsInParent().getMaxY(), 10, 2, false);
    }


}
