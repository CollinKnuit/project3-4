package org.FF.GUI.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.FF.GUI.common.SerialConnection;

public class KeypadListener implements ActionListener{
	
	private ImgBackgrounds imgSelectorA;
	private ImgBackgrounds imgSelectorB;
	private ImgBackgrounds imgSelectorC;
	private ImgBackgrounds imgSelectorD;
	private Painter painter;
	private SerialConnection serialConnection;
	
	/**
	 * 
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */
	public KeypadListener(Painter painter, SerialConnection serialConnection) {
		this.painter = painter;
		this.serialConnection = serialConnection;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// gets the input from the keypad
		var commands = serialConnection.getWaitingQueue();
		// for each input loop it
		for(var c: commands) {	
			
			switch(c) {
			  
			  case "A":
				  painter.switchPane(imgSelectorA);
				  break;
			  case "B":
				  painter.switchPane(imgSelectorB);
				  break;
			  case "C":
				  painter.switchPane(imgSelectorC);
				  break;
			  case "D":
				  painter.switchPane(imgSelectorD);
				  break;
			  case "*":
				  break;
			  case "#":
				  break;
			  
			  default:
				  break;
				 
			}
		}
	}
	
	
	/**
	 * Sets the img to change to
	 * @param imgSelectorA 	is button A {@code ImgBackgrounds}
	 * @param imgSelctorB	is button B	{@code ImgBackgrounds}
	 * @param imgSelectorC	is button C {@code ImgBackgrounds}
	 * @param imgSelectorD	is button D	{@code ImgBackgrounds}
	 */	
	public synchronized void setImgSelectors(ImgBackgrounds imgSelectorA, ImgBackgrounds imgSelctorB, ImgBackgrounds imgSelectorC, ImgBackgrounds imgSelectorD) {
		this.imgSelectorA = imgSelectorA;
		this.imgSelectorB = imgSelctorB;
		this.imgSelectorC = imgSelectorC;
		this.imgSelectorD = imgSelectorD;
	}

}
