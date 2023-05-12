package router;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import config.Config;
import config.Constant;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Game;
import pages.Welcome;

/**
 * Setup primary stage, scene and all pages.
 * Setting up primary stage includes app name, dimension, icons, etc.
 * <br />
 * Provide methods to navigate through pages.
 */
public final class Router {
    private static Router instance;

    private HashMap<Page, IPage> pages = new HashMap<Page, IPage>();
    private Stage stage;
    private Scene scene;

    private Stack<Page> history = new Stack<Page>();
    private Page currentPage = Page.WELCOME;

    private Router(Stage stage) throws IOException {
        this.stage = stage;

        this.pages.put(Page.WELCOME, new Welcome());
        this.pages.put(Page.GAME, new Game());

        for (var page : this.pages.values()) {
            page.initialize();
        }

        this.scene = new Scene(this.pages.get(Page.WELCOME).getNode(),
                Config.SCREEN_HEIGHT,
                Config.SCREEN_WIDTH);

        this.stage.setScene(this.scene);
        this.stage.setTitle(Constant.APP_NAME);
        this.stage.setResizable(false);
        this.stage.show();
    }

    private void setScenePage(Page pageKey) {
        var page = this.pages.get(pageKey);
        var current = this.pages.get(this.getCurrentPage());

        current.onNavigatedFrom();
        this.scene.setRoot(page.getNode());
        page.onNavigatedTo();

        this.currentPage = pageKey;
    }

    /**
     * Navigate to a page and add that to router history.
     * 
     * @param pageKey
     *            enum for destination page
     */
    public synchronized void push(Page pageKey) {
        this.setScenePage(pageKey);
        this.history.push(pageKey);
    }

    /**
     * Go back to previous page. Has no effect on welcome page.
     */
    public synchronized void pop() {
        try {
            var pageKey = this.history.pop();
            this.setScenePage(pageKey);
        } catch (EmptyStackException e) {
            this.setScenePage(Page.WELCOME);
        }
    }

    /**
     * Navigate to a page, replacing current page in router history,
     * 
     * @param pageKey
     *            enum for destination page
     */
    public synchronized void replace(Page pageKey) {
        if (!this.history.isEmpty()) {
            this.history.pop();
        }

        this.push(pageKey);
    }

    /**
     * Create a router instance. <b>Do not call this method more than once!</b>
     * 
     * @param stage
     * @return A singleton router instance
     * @throws IOException
     *             May occur when loading resources of a some pages.
     */
    public static synchronized Router createInstance(Stage stage)
            throws IOException {
        Router.instance = new Router(stage);
        return Router.instance;
    }

    /**
     * Get current (a singleton) instance of a router. Required to access
     * navigation methods.
     * 
     * <p>
     * Example Usage
     * </p>
     * 
     * <pre>
     * <code>
     * button.setOnAction(e -> {
     *     Router.getInstance().push(Page.GAME);
     * });
     * </code>
     * </pre>
     */
    public static synchronized Router getInstance() {
        return Router.instance;
    }

    public Page getCurrentPage() {
        return this.currentPage;
    }
}
