package com.fapse.mandelbrot.view;

import java.util.Observable;
import java.util.Observer;
import com.fapse.mandelbrot.MainApp;
import com.fapse.mandelbrot.MainApp.ImageChange;
//import com.fapse.mandelbrot.model.Mandelbrot.ImageChange;
import com.fapse.mandelbrot.model.MandelbrotFacade;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MandelbrotController implements Observer {
	private MandelbrotFacade mf;
	private MainApp mainApp;
	private int width = 800, height = 600;
	FadeTransition fadeInUI, fadeOutUI;
	@FXML
	private StackPane stackPane = new StackPane();
	@FXML
	private HBox hBox = new HBox();
	@FXML
	private ImageView iv = new ImageView();
	
	public MandelbrotController() {
	}

	@FXML
	private void initialize() {
		mf = new MandelbrotFacade();
		mf.addObserver(this);
		mf.orderImage(width, height, ImageChange.NONE);
		stackPane.widthProperty().addListener(e -> width = (int) stackPane.getWidth());
		stackPane.heightProperty().addListener(e -> height = (int) stackPane.getHeight());
		hBox.setOpacity(0);
		fadeInUI = new FadeTransition(Duration.millis(200), hBox);
		fadeInUI.setToValue(1);
		fadeOutUI = new FadeTransition(Duration.millis(200), hBox);
		fadeOutUI.setToValue(0);
	}
	
	@FXML
	private void handleMouseEnteredHBox() {
		fadeInUI.play();
	}

	@FXML
	private void handleMouseExitedHBox() {
		fadeOutUI.play();
	}

	@FXML
	private void handleMoveLeft() {
		mf.orderImage(width, height, ImageChange.LEFT);
	}
	
	@FXML
	private void handleMoveUp() {
		mf.orderImage(width, height, ImageChange.UP);
	}
	
	@FXML
	private void handleMoveDown() {
		mf.orderImage(width, height, ImageChange.DOWN);
	}
	
	@FXML
	private void handleMoveRight() {
		mf.orderImage(width, height, ImageChange.RIGHT);
	}

	@FXML
	private void handleZoomIn() {
		mf.orderImage(width, height, ImageChange.ZOOM_IN);
	}

	@FXML
	private void handleZoomOut() {
		mf.orderImage(width, height, ImageChange.ZOOM_OUT);
	}
		
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;		
	}

	@Override
	public void update(Observable o, Object arg) {
		iv.setImage(mf.getImage());
	}
}