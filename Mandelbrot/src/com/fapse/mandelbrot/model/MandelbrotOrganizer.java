package com.fapse.mandelbrot.model;

import java.util.LinkedList;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

class MandelbrotOrganizer {
	private MandelbrotParams mp;
	private MandelbrotFacade mf;
	private WritableImage writImg = null;
	private PixelWriter pixWrit = null;
	
	MandelbrotOrganizer(MandelbrotParams mp, MandelbrotFacade mf) {
		this.mp = mp;
		this.mf = mf;
	}
	
	void orderImage() {
		Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors() - 1];
		Runnable runnable;
		LinkedList<Integer> stripeBoundaries = new LinkedList<>();
		int stripe_width = 0, stripeCounter = 0;
		writImg = new WritableImage(mp.getWidth(), mp.getHeight());
		pixWrit = writImg.getPixelWriter();
		//calculate upper and lower boundaries for image stripes
		//upper boundary is lower boundary of next stripe
		stripe_width = mp.getHeight() / threads.length;
		while (stripeCounter < mp.getHeight()) {
			stripeBoundaries.addFirst(new Integer(stripeCounter));
			if (stripeCounter + stripe_width < mp.getHeight()) {
				stripeCounter += stripe_width;
			} else {
				stripeCounter = mp.getHeight();
				stripeBoundaries.addFirst(new Integer(stripeCounter));
			}
		}

		//start thread for each image stripe
		for (int n = 0; n < threads.length; n++) {
			runnable = new MandelbrotComputer(stripeBoundaries.poll(), stripeBoundaries.peek(), mp, this);
			threads[n] = new Thread(runnable); 
			threads[n].start();
		}
		//wait for each thread to finish
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mf.setChanged();
	}
	
	//add separate image strips together, called by each image stripe thread
	synchronized void buildImage(int lower, int upper, Color[] imgData) {
		int x = 0, y = 0, n = 0;
		for (x = 0; x < mp.getWidth(); x++) {
			for (y = lower; y < upper; y++) {
				pixWrit.setColor(x, y, imgData[n++]);;
			}
		}
	}
	
	WritableImage getImage() {
		return writImg;
	}
}