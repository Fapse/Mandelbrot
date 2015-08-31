package com.fapse.mandelbrot.model;

import java.util.Observable;
import com.fapse.mandelbrot.MainApp.ImageChange;
import javafx.scene.image.WritableImage;

public class MandelbrotFacade extends Observable implements Runnable {
	private MandelbrotParams mp = new MandelbrotParams();
	private MandelbrotOrganizer mo = new MandelbrotOrganizer(mp, this);
	private Thread thread = null;
		
	public void orderImage(int width, int height, ImageChange imageChange) {
		if ((thread == null) || (!thread.isAlive())) {
		mp.setParams(width, height, imageChange);
		thread = new Thread(this);
		thread.start();
		} else {
			//TODO: give Feedback to user: System busy
		}
	}
	
	public WritableImage getImage() {
		return mo.getImage();
	}
	
	@Override
	protected void setChanged() {
		super.setChanged();
		super.notifyObservers();
	}

	@Override
	public void run() {
		mo.orderImage();
	}
}