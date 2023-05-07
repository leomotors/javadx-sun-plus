package router;

import java.io.IOException;

import javafx.scene.Parent;

public interface IPage {
    public void initialize() throws IOException;

    public Parent getNode();
}
