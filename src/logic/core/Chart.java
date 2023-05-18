package logic.core;

import java.util.Map;

import javafx.scene.image.Image;

public record Chart(String id, String title, String author, Image image,
        Map<Difficulty, String> difficulties) {

}
