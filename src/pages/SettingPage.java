package pages;

import java.io.IOException;

import controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import router.Page;

public class SettingPage implements Page {
    private Parent node;

    private BaseController controller;

    @Override
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("SettingPage.fxml"));
        this.node = loader.load();
        controller = loader.getController();
    }

    @Override
    public Parent getNode() {
        return this.node;
    }

    @Override
    public void startPage() {
        controller.start();
    }
}
