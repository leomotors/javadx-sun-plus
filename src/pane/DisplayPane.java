package pane;

import component.TodoItem;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DisplayPane extends VBox {
    private final ArrayList<ArrayList<TodoItem>> todoLists = new ArrayList<>();
    public DisplayPane() {
    	this.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        this.setPadding(new Insets(20));
        this.setSpacing(10);
    }

    public void addTodoList() {
    	ArrayList<TodoItem> todo = new ArrayList<TodoItem>();
        todoLists.add(todo);
    }

    public void setActiveTodoList(int index) {
        if(index < 0 || index >= this.todoLists.size()) {
        	return;
        }
        this.getChildren().clear();
        for(TodoItem e: todoLists.get(index)) {
        	this.getChildren().add(e);
        }
    }

    public void addTodoItem(TodoItem todoItem) {
        int currentPage = RootPane.getNavigationPane().getCurrentPage();
        this.todoLists.get(currentPage).add(todoItem);
        this.getChildren().add(todoItem);
    }

    public void removeTodoItem(TodoItem todoItem) {
        int currentPage = RootPane.getNavigationPane().getCurrentPage();
        this.todoLists.get(currentPage).remove(todoItem);
        this.getChildren().remove(todoItem);
    }
}
