package pages;

import java.io.IOException;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import router.Page;

public class Game implements Page {
    private Parent node;

    private GameController controller;

    @Override
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        this.node = loader.load();
        controller = loader.getController();
    }

    @Override
    public Parent getNode() {
        return this.node;
    }

    public void startPage() {
        controller.start();
    }
}
