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

    public synchronized void push(Page pageKey) {
        this.setScenePage(pageKey);
        this.history.push(pageKey);
    }

    public synchronized void pop() {
        try {
            var pageKey = this.history.pop();
            this.setScenePage(pageKey);
        } catch (EmptyStackException e) {
            this.setScenePage(Page.WELCOME);
        }
    }

    public synchronized void replace(Page pageKey) {
        if (!this.history.isEmpty()) {
            this.history.pop();
        }

        this.push(pageKey);
    }

    public static synchronized Router createInstance(Stage stage)
            throws IOException {
        Router.instance = new Router(stage);
        return Router.instance;
    }

    public static synchronized Router getInstance() {
        return Router.instance;
    }

    public Page getCurrentPage() {
        return this.currentPage;
    }
}
