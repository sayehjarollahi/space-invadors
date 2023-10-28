import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Shield extends ImageView {
    public Shield(double x, double y) {
        Image image = new Image("shield.png", 200, 50, true, true);
        this.setX(x);
        this.setY(y);
        this.setImage(image);
    }

}
