package com.fapse.mandelbrot.model;

import static org.junit.Assert.*;
import org.junit.Test;

import javafx.scene.paint.Color;

public class MandelbrotComputerTest {
	MandelbrotFacade mf = new MandelbrotFacade();
	MandelbrotParams mp = new MandelbrotParams();
	MandelbrotOrganizer mo = new MandelbrotOrganizer(mp, mf);
	MandelbrotComputer mc = new MandelbrotComputer(400, 200, mp, mo);
	@Test
	public void easyPeasyTest() {
		assertTrue(2 == 2);
	}
	
	@Test
	public void setColorTest() {
		Color color = mc.setColor(20);
		assertNotNull(color);
	}
}
