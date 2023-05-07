package utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {
    public static ImageView loadImage(String imagePath) {
        var path = ClassLoader.getSystemResource(imagePath).toString();

        return new ImageView(new Image(path));
    }

    public static ImageView loadImage(String imagePath, double width,
            double height) {
        var path = ClassLoader.getSystemResource(imagePath).toString();

        return new ImageView(new Image(path, width, height, true, true));
    }
}
