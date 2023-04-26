package application;

import config.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
    	//create root
    	try {    		
			Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
			Scene scene = new Scene(root,Config.SCREEN_HEIGHT,Config.SCREEN_WIDTH);
			stage.setScene(scene);
	    	stage.setTitle("Java DX Sun Plus");
	    	stage.setResizable(false);
	    	stage.show();    	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) {
        launch();
    }
}
