package com.fapse.mandelbrot;
	
import com.fapse.mandelbrot.view.MandelbrotController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class MainApp extends Application {
	
	private Stage stage;
	private StackPane rootLayout;
	public enum ImageChange {
		UP, DOWN, LEFT, RIGHT, ZOOM_IN, ZOOM_OUT, NONE, RESET;
	}
	
	@Override
	public void start(Stage stage) {
		try {
			this.stage = stage;
			this.stage.setTitle("Apfelmännchen");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MandelbrotView.fxml"));
			rootLayout = (StackPane) loader.load();
			Scene scene = new Scene(rootLayout);
			this.stage.setScene(scene);
			MandelbrotController controller = loader.getController();
			controller.setMainApp(this);
			this.stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
