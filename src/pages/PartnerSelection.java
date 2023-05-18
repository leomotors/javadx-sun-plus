package pages;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import router.Page;

public class PartnerSelection implements Page {
    private Parent node;

    @Override
    public void initialize() throws IOException {
        this.node = FXMLLoader
                .load(getClass().getResource("PartnerSelection.fxml"));

    }

    @Override
    public Parent getNode() {
        return this.node;
    }
}
