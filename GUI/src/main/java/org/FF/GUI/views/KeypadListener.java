package org.FF.GUI.views;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.FF.GUI.common.SerialConnection.SerialConnection;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeypadListener extends Thread{
	
	private ImgBackgrounds imgSelectorA;
	private ImgBackgrounds imgSelectorB;
	private ImgBackgrounds imgSelectorC;
	private ImgBackgrounds imgSelectorD;
    private ImgBackgrounds imgSelectorS;
    private ImgBackgrounds imgSelectorH;
	private ImgBackgrounds imgSelectorG;
	protected AtomicBoolean exit = new AtomicBoolean(false);
	private Painter painter;
	private SerialConnection serialConnectionKeypad;
	private SerialConnection serialConnectionBonprinter;
	private AtomicBoolean suspend = new AtomicBoolean(true);
	private String input = "";
	
	/**
	 * 
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */
	public KeypadListener(Painter painter, ArrayList<SerialConnection> serialConnection) {
		this.painter = painter;
		this.serialConnectionKeypad = serialConnection.get(0);
		this.serialConnectionBonprinter = serialConnection.get(3);
	}
	

	/**
	 * 
	 */
	public void suspendThread() {
		synchronized (this) {
			serialConnectionKeypad.removePortListener();
			suspend.compareAndSet(false, true);		
		}
	}
	
	/**
	 * 
	 */
	public void activateThread() {
		synchronized (this) {
			serialConnectionKeypad.addPortListener();
			suspend.compareAndSet(true, false);	
			this.notify();
		}
		
	}
	
	@Override
	public void run() {
		serialConnectionKeypad.addPortListener();
		while(!exit.get()) {
			
			synchronized (this) {
				while(suspend.get()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			var commands = serialConnectionKeypad.getWaitingQueue();
			var screen = painter.getScreen();
			for(var c: commands) {	

				switch(c) {
					case "A":
						if(imgSelectorA != null) {
							painter.switchPane(imgSelectorA);
						}
						break;
				  	
					case "B":
						if(imgSelectorB != null) {
							painter.switchPane(imgSelectorB);
						}
						
						break;
				  	
					case "C":
						if(imgSelectorC != null) {
							painter.switchPane(imgSelectorC);
						}
						
						break;
				  	
					case "D":
						if(imgSelectorD != null) {
							painter.switchPane(imgSelectorD);
						}
						
						break;
				  	
					case "*":
			  		
			  	  		if(screen ==  ImgBackgrounds.FB1_1) {
			  	  			printBon(70);	
			  	  		} 
			  	  		else{
			  	  			backspace(screen);
			  	  		}

			  	  		break;
				  	
					case "#":
						if(screen == ImgBackgrounds.FB1_1){
							painter.switchPane(imgSelectorH);
						} 
						else {
							enter(screen);
						}
			  	    
						break;
					
					default:
						if(screen == ImgBackgrounds.FP1_1) {
							choiceScreen(c);
							painter.switchPane(ImgBackgrounds.FB1_1);
							break;
						}
						
						this.input += c;
			  		
						if(screen == ImgBackgrounds.FV1_1 ){	
							painter.setAmount(input);
							break;
						
						}

						if(screen == ImgBackgrounds.FL1_1){
					
							painter.setPassword(input);
							break;
						}
				
						break;
				}
			}
		}
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean stopThread() {
		return exit.compareAndSet(false, true);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean startThread() {
		return exit.compareAndSet(true, false);
	}
		
	/**
	 * 
	 * @param a
	 */
	private void choiceScreen(String a){
		switch(a) {
		  	case "1":
				//kies 10 euro
			
				break;
			case "2":
				//kies 20 euro
				
				break;
			case "3":
				//kies 50 euro
			
				break;
			case "4":
				//kies 100 euro
				
				break;
		}
	}
	
	/**
	 * 
	 * @param a
	 */
	private void enter(ImgBackgrounds a) {
		if(input == "") return;
		
		switch(a) {
		  	case FV1_1:
			  	var amount = Integer.parseInt(this.input);
			  	
			  	if(!checkIfLegitSum(amount)) {
			  		this.input = "";
			  		painter.setAmount("");
			  		return;
			  	}
			
			  	var d = new BigDecimal(amount);
				if (painter.getAcount().getBalance().compareTo(d) == 1 ) {
					painter.setAmount(Integer.toString(amount));
					painter.switchPane(imgSelectorH);
					this.input = "";
				} else {
					painter.setAmount("");
					this.input = "";
				}
				break;
			case FL1_1:
				
				try {
				
					var hashmap = painter.getQuery().checkPassword(painter.getAcountID(), this.input);
					
					if(hashmap.containsKey(false)) {
						
					}
					else {
						painter.setAccount(painter.getQuery().getAcountInfo(painter.getAcountID()));
						painter.switchPane(ImgBackgrounds.FH1_1);
					}
					input = "";
					painter.setPassword(input);
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		default:
			break;
		
		}					
	}

	/**
	 * 
	 * @param screen
	 */
	private void backspace(ImgBackgrounds screen) {
		if(input.length() > 0) {
			input = input.substring(0, input.length() - 1);
			
			if(screen == ImgBackgrounds.FV1_1 ){	
				painter.setAmount(input);
			
			}
			else if(screen == ImgBackgrounds.FL1_1){
				painter.setPassword(input);
				
			}
			
		} else {
			input = "";
		}
	}
	


	/**
	 * 
	 * @param amount
	 * @return
	 */
	private synchronized Boolean checkIfLegitSum(int amount) {
		int remainder = Math.abs(amount % 10);
		if(remainder != 0) {
			return false;
		}
		
		return true;

		
	}

	/**
	 * 
	 * @param input
	 */
	private void printBon(int input){
		//serialConnectionBonprinter.sendData("1");
		//serialConnectionBonprinter.sendData(Integer.toString(amountOutput));
		painter.switchPane(imgSelectorS);	
	}
	
	
	
	public SerialConnection getSerialConnectionKeypad() {
		return serialConnectionKeypad;
	}


	/**
	 * Sets the img to change to
	 * @param imgSelectorA 	is button A {@code ImgBackgrounds}
	 * @param imgSelectorB	is button B	{@code ImgBackgrounds}
	 * @param imgSelectorC	is button C {@code ImgBackgrounds}sss
	 * @param imgSelectorD	is button D	{@code ImgBackgrounds}
     * @param imgSelectorS	is button D	{@code ImgBackgrounds}
	 * @param imgSelectorH	is button H	{@code ImgBackgrounds}
	 * @param imgSelectorG is button 1, 2, 3 or 4 {@code ImgBackgrounds}
	 */	
	public synchronized void setImgSelectors(ImgBackgrounds imgSelectorA, ImgBackgrounds imgSelectorB, 
											 ImgBackgrounds imgSelectorC, ImgBackgrounds imgSelectorD, 
											 ImgBackgrounds imgSelectorS, ImgBackgrounds imgSelectorH, 
											 ImgBackgrounds imgSelectorG 								 ) {
		
		this.imgSelectorA = imgSelectorA;
		this.imgSelectorB = imgSelectorB;
		this.imgSelectorC = imgSelectorC;
		this.imgSelectorD = imgSelectorD;
		this.imgSelectorS = imgSelectorS;
		this.imgSelectorH = imgSelectorH;
		this.imgSelectorG = imgSelectorG;
	}

	
}