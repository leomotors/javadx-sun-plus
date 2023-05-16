package router;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import constant.Config;
import constant.Constant;
import constant.Resource;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Game;
import pages.Welcome;
import utils.ImageUtil;

/**
 * Setup primary stage, scene and all pages.
 * Setting up primary stage includes app name, dimension, icons, etc.
 * <br />
 * Provide methods to navigate through pages.
 */
public final class Router {
    private static Router instance;

    private HashMap<AppPage, Page> pages = new HashMap<AppPage, Page>();
    private Stage stage;
    private Scene scene;

    private Stack<AppPage> history = new Stack<AppPage>();
    private AppPage currentPage = AppPage.WELCOME;

    private Router(Stage stage) throws IOException {
        this.stage = stage;

        this.pages.put(AppPage.WELCOME, new Welcome());
        this.pages.put(AppPage.GAME, new Game());

        for (var page : this.pages.values()) {
            page.initialize();
        }

        this.scene = new Scene(this.pages.get(AppPage.WELCOME).getNode(),
                Config.SCREEN_HEIGHT,
                Config.SCREEN_WIDTH);

        this.stage.setScene(this.scene);
        this.stage.setTitle(Constant.APP_NAME);
        this.stage.setResizable(false);

        var appIcon = ImageUtil.loadImage(Resource.JAVADX_LOGO_SQUARE);
        this.stage.getIcons().add(appIcon);

        this.stage.show();
    }

    private void setScenePage(AppPage pageKey) {
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
    public synchronized void push(AppPage pageKey) {
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
            this.setScenePage(AppPage.WELCOME);
        }
    }

    /**
     * Navigate to a page, replacing current page in router history,
     * 
     * @param pageKey
     *            enum for destination page
     */
    public synchronized void replace(AppPage pageKey) {
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

    public AppPage getCurrentPage() {
        return this.currentPage;
    }
}
