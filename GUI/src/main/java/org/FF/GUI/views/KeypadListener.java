package org.FF.GUI.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;

import java.util.ArrayList;

public class KeypadListener implements ActionListener{
	
	private ImgBackgrounds imgSelectorA;
	private ImgBackgrounds imgSelectorB;
	private ImgBackgrounds imgSelectorC;
	private ImgBackgrounds imgSelectorD;
    private ImgBackgrounds imgSelectorS;
    private ImgBackgrounds imgSelectorH;
	private ImgBackgrounds imgSelectorG;
	private Painter painter;
	private Acount account;
	private SerialConnection serialConnectionKeypad;
	private SerialConnection serialConnectionBonprinter;
	private String input = "";
	
	/**
	 * 
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */
	public KeypadListener(Painter painter, ArrayList<SerialConnection> serialConnection) {
		this.painter = painter;
		this.serialConnectionKeypad = serialConnection.get(0);
		this.serialConnectionBonprinter = serialConnection.get(1);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// gets the input from the keypad
		var commands = serialConnectionKeypad.getWaitingQueue();

		for(var c: commands) {	

			if(painter.getScreen() == ImgBackgrounds.FV1_1 ){
				if(reggexMatch(c)){
					painter.setAmount(input);
					continue;
				}
			}

			if(painter.getScreen() == ImgBackgrounds.FL1_1){
				if(reggexMatch(c)){
					painter.setPassword(input);
					continue;
				}
			}

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
				  
			  	  	if(painter.getScreen().contentEquals("FB1_1")) {
						printBon();	
					} 
					else{
						backspace();
				  	}

				  	break;
			  	case "#":
			  	    if(painter.getScreen() == ImgBackgrounds.FB1_1){
						painter.switchPane(imgSelectorH);
				    } else if(painter.getScreen() == ImgBackgrounds.FV1_1) {
						// String amount = this.painter.getAmount();
						enter();
					}
					break;
			  	default:


				break;
				 
			}
		}
	}
		

	private boolean reggexMatch (String c){
		if(c.matches("\\d+")) {
			this.input += c;
			return true;
		}
		return false;
	}

	private void choiceScreen(String a){
		switch(a) {
		  	case "1":
				//kies 10 euro
				System.out.println("vast bedrag van 10 euro");
				break;
			case "2":
				//kies 20 euro
				System.out.println("vast bedrag van 20 euro");
				break;
			case "3":
				//kies 50 euro
				System.out.println("vast bedrag van 50 euro");
				break;
			case "4":
				//kies 100 euro
				System.out.println("vast bedrag van 100 euro");
				break;
		}
	}
	
	public void enter(ImgBackgrounds a) {
		if(input == "") return;
		
		switch(a) {
		  	case FV1_1:
			  	
			  	int amountOutput = calculateAmount(Integer.parseInt(this.input));
			
				if (account.getBalance().intValue() >= amountOutput) {
					painter.setAmount(Integer.toString(amountOutput));
					painter.switchPane(imgSelectorH);
					this.input = "";
				} else {
					painter.setAmount("");
					this.input = "";
				}
				break;
			case FL1_1:
				painter.getQuery().checkPassword(painter.getAcountID(), this.input);
				break;
		
		}					
	}

	public void backspace() {
		if(input.length() > 0) {
			input = input.substring(0, input.length() - 1);
		} else {
			input = "";
		}
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
	public synchronized void setImgSelectors(ImgBackgrounds imgSelectorA, ImgBackgrounds imgSelectorB, ImgBackgrounds imgSelectorC, ImgBackgrounds imgSelectorD, ImgBackgrounds imgSelectorS, ImgBackgrounds imgSelectorH, ImgBackgrounds imgSelectorG) {
		this.imgSelectorA = imgSelectorA;
		this.imgSelectorB = imgSelectorB;
		this.imgSelectorC = imgSelectorC;
		this.imgSelectorD = imgSelectorD;
		this.imgSelectorS = imgSelectorS;
		this.imgSelectorH = imgSelectorH;
		this.imgSelectorG = imgSelectorG;
	}

	public synchronized int calculateAmount(int amount) {
		int printAmount = amount;
		int remainder = printAmount % 10;
		if(printAmount < 250 && printAmount > 10) {
			if( remainder > 5) {
				printAmount += (10 - remainder);
			} else {
				printAmount -= remainder;
			}
		} else if (printAmount > 250) {
			printAmount = 250;
		} else {
			printAmount = 10;
		}
		
		System.out.println("the calculated amount: " + printAmount);
		return printAmount;

		
	}

	public void printBon(){
		int amountOutput = calculateAmount(Integer.parseInt(input));
		serialConnectionBonprinter.sendData("1");
		serialConnectionBonprinter.sendData(Integer.toString(amountOutput));
		System.out.println("1 gestuurd");
		System.out.println(amountOutput);
		painter.switchPane(imgSelectorS);	
	}
	
	public String getRfidNumber() {
		return this.account;
	}
	
	public void setAccount(Acount acount) {
		this.account = acount;
	}


}