package router;

import java.io.IOException;

import javafx.scene.Parent;

/**
 * An interface for something that can be page.
 * The app can switch to different pages using {@link Router}
 */
public interface Page {
    public void initialize() throws IOException;

    public default void onBeforeNavigatedFrom() {}

    public default void onAfterNavigatedFrom() {}

    public default void onBeforeNavigatedTo() {}

    public default void onAfterNavigatedTo() {}

    public default void startPage() {}

    public Parent getNode();
}
