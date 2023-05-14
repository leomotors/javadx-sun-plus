package router;

import java.io.IOException;

import javafx.scene.Parent;

/**
 * An interface for something that can be page.
 * The app can switch to different pages using {@link Router}
 */
public interface Page {
    public void initialize() throws IOException;

    public default void onNavigatedFrom() {}

    public default void onNavigatedTo() {}

    public Parent getNode();
}
