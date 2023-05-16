package utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class ImageUtil {
    private ImageUtil() {}

    public static Image loadImage(String imagePath) {
        var path = ClassLoader.getSystemResource(imagePath).toString();

        return new Image(path);
    }

    public static Image loadImage(String imagePath, double width,
            double height) {
        var path = ClassLoader.getSystemResource(imagePath).toString();

        return new Image(path, width, height, true, true);
    }

    public static ImageView loadImageAsView(String imagePath) {
        return new ImageView(ImageUtil.loadImage(imagePath));
    }

    public static ImageView loadImageAsView(String imagePath, double width,
            double height) {
        return new ImageView(ImageUtil.loadImage(imagePath, width, height));
    }

}
