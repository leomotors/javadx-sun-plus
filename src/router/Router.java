package router;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

import config.Config;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Game;
import pages.Welcome;

public class Router {
    private static Router instance;

    private HashMap<Pages, IPage> pages;
    private Stage stage;
    private Scene scene;

    private Stack<Pages> history;

    private Router(Stage stage) throws IOException {
        this.stage = stage;
        this.pages = new HashMap<Pages, IPage>();

        this.pages.put(Pages.WELCOME, new Welcome());
        this.pages.put(Pages.GAME, new Game());

        for (var page : this.pages.values()) {
            page.initialize();
        }

        this.scene = new Scene(this.pages.get(Pages.WELCOME).getNode(),
                Config.SCREEN_HEIGHT,
                Config.SCREEN_WIDTH);

        this.stage.setScene(this.scene);
        this.stage.setTitle("JavaDX SUN PLUS!");
        this.stage.setResizable(false);
        this.stage.show();

        this.history = new Stack<Pages>();
    }

    private void setScenePage(Pages pageKey) {
        var page = this.pages.get(pageKey);
        this.scene.setRoot(page.getNode());
    }

    public synchronized void push(Pages pageKey) {
        this.setScenePage(pageKey);
        this.history.push(pageKey);
    }

    public synchronized void pop() {
        try {
            var pageKey = this.history.pop();
            this.setScenePage(pageKey);
        } catch (EmptyStackException e) {
            this.setScenePage(Pages.WELCOME);
        }
    }

    public static synchronized Router createInstance(Stage stage)
            throws IOException {
        Router.instance = new Router(stage);
        return Router.instance;
    }

    public static synchronized Router getInstance() {
        return Router.instance;
    }
}
