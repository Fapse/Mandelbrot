package com.fapse.mandelbrot.model;

import javafx.scene.paint.Color;

class MandelbrotComputer implements Runnable {
	private int upperBoundary, lowerBoundary;
	private Color imgData[];
	private MandelbrotParams mp;
	private MandelbrotOrganizer mo;
	
	MandelbrotComputer(int upper, int lower, MandelbrotParams mp, MandelbrotOrganizer mo) {
		this.upperBoundary = upper;
		this.lowerBoundary = lower;
		this.mp = mp;
		this.mo = mo;
		imgData = new Color[(upper - lower) * mp.getWidth()];
	}

	@Override
	public void run() {
		computeImageStripe();
	}
	
	private void computeImageStripe() {
		int pixelX = 0, pixelY = 0, iterationValue, colorValue, counter = 0;
		double cx = 0, cy = 0;
		for (pixelX = 0; pixelX < mp.getWidth(); pixelX++) {
			cx = computeComplementX(pixelX);
			for (pixelY = lowerBoundary; pixelY < upperBoundary; pixelY++) {
				cy = computeComplementY(pixelY);
				iterationValue = iteratePoint(cx, cy);
				colorValue = normalizeColor(iterationValue);
				imgData[counter++] = setColor(colorValue);
			}
		}
		mo.buildImage(lowerBoundary, upperBoundary, imgData);
	}
	
	private double computeComplementX(int pixelX) {
		return (double)(pixelX * mp.getX_up() / mp.getWidth() - mp.getX_lo());
	}
	
	private double computeComplementY(int pixelY) {
		return (double)(pixelY * mp.getY_up() / mp.getHeight() - mp.getY_lo());
	}

	private int iteratePoint(double cx, double cy) {
		int maxAbsValSquared = 4, iterationCounter = 0;
		double x = 0, y = 0, xt, absValSquared = 0;
		while ((absValSquared <= maxAbsValSquared) && (iterationCounter < mp.getMaxIterations())) {
			xt = x * x - y * y + cx;
			y = 2 * x * y + cy;
			x = xt;
			iterationCounter++;
			absValSquared = x * x + y * y;
		}	
		return iterationCounter;
	}
	
	private int normalizeColor(int iterationValue) {
		int colorValue;
		if (iterationValue < mp.getMaxIterations()) {
			colorValue = (int)(((float) iterationValue / (float) mp.getMaxIterations()) * (float) 799);
		} else {
			colorValue = 0;
		}
		return colorValue;
	}
	
	protected Color setColor(int colorValue) {
		int r = 0, g = 0, b = 0;
		if (colorValue < 32) {
			b = colorValue * 8;
		} else if (colorValue < 288) {
			g = colorValue - 32;
			b = 287 - colorValue;
		} else if (colorValue < 544) {
			r = colorValue - 288;
			g = 543 - colorValue;
		} else if (colorValue < 800) {
			r = 255;
			g = b = colorValue - 544;			
		}
		return Color.rgb(r, g, b);
	}
}