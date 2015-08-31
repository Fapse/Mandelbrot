package com.fapse.mandelbrot.model;

import com.fapse.mandelbrot.MainApp.ImageChange;

class MandelbrotParams {
	private int width = 800, height = 600;
	private double y_up = 3f;
	private double y_lo = 1.5;
	private double x_up = y_up / height * width;
	private double x_lo = y_lo / height * width + .7; 
	private int maxIterations = 5000;
	
	public void setParams(int width, int height, ImageChange imageChange) {
		this.width = width;
		this.height = height;
		switch(imageChange) {
		case NONE:
			break;
		case UP:
			y_lo -= x_up * .1 * height / width;			
			break;
		case DOWN:
			y_lo += x_up * .1 * height / width;
			break;
		case LEFT:
			x_lo -= x_up * .1;
			break;
		case RIGHT:
			x_lo += x_up * .1;
			break;
		case ZOOM_IN:
			double y_up_temp = y_up;
			double x_up_temp = x_up;
			y_up *= .9;
			y_lo = y_lo - (y_up_temp - y_up) / 2;
			x_up = y_up / height * width;
			x_lo = x_lo - (x_up_temp - x_up) / 2;
			break;
		case ZOOM_OUT:
			y_up_temp = y_up;
			x_up_temp = x_up;
			y_up *= 1.1;
			y_lo = y_lo + (y_up - y_up_temp) / 2;
			x_up = y_up / height * width;
			x_lo = x_lo + (x_up - x_up_temp) / 2;
			break;
		case RESET:
			y_up = 3f;
			y_lo = 1.5;
			x_lo = y_lo / height * width + .7; 
			x_up = y_up / height * width;
			maxIterations = 5000;
		}
		return;
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	double getY_up() {
		return y_up;
	}

	double getY_lo() {
		return y_lo;
	}

	double getX_lo() {
		return x_lo;
	}

	double getX_up() {
		return x_up;
	}

	int getMaxIterations() {
		return maxIterations;
	}
}