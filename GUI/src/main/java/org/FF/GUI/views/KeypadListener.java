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
	
	/**
	 * If exit is false otherwise continue with the method
	 * if suspend is false wait otherwise continue with the method
	 * store the input from the keypad in commands and store the current screen in screen
	 * if commands matches A,B,C,D change the displayed screen to that specific imgSelector
	 * if commands matches * do either backspace or change the displayed screen to the welcome screen depending on the current screen
	 * if commands matches # either do enter or change the displayed screen to the welcome screen depending on the current screen
	 * else depending on the current screen run choiceScreen, setAmount or setPassword.
	 */
	@Override
	public void run() {
		serialConnectionKeypad.addPortListener();
		while(!exit.get()) {
			
			synchronized (this) {
				while(suspend.get()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
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
	 * depending on the parameter a (amount) print 10, 20, 50 euro's
	 * 
	 * @param a
	 */
	private void choiceScreen(String a){
		input = "";
		switch(a) {
		  	case "1":
				//kies 10 euro
		  		input = "10";
				break;
			case "2":
				//kies 20 euro
				input = "20";
				break;
			case "3":
				//kies 50 euro
				input = "50";
				break;
			case "4":
				input = "100";
				break;
		}
		
		enter(ImgBackgrounds.FP1_1);
	}
	
	/**
	 * if the parameter screen equals FV1_1 check if the amount is legit. If not, clear input and the displayed amount
	 * else if balance from acount is bigger than amount put amount on the screen and clear input
	 * else clear input and the displayed amount and display the error message
	 * 
	 * if the parameter screen equals FL1_1 check if input is a pair with acountID (from acount) in hasmap. If so request acountInfo 
	 * from acount out of the database and update acount with the values from the query in the method getAcountInfo and continue to the homescreen
	 * and after that clear input and the displayed password and display the error message
	 * to display the error message properly the method stores the amount of wrong attempts from the query and gives it to the method displaying the error message.
	 * @param s
	 * @throws SQLException 
	 */
	private void enter(ImgBackgrounds screen)  {
		if(input == "") return;
		
		switch(screen) {
		  	case FV1_1:
			try {
				withdrawMoney();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				break;
			case FL1_1:
				
				try {
				
					var hashmap = painter.getQuery().checkPassword(painter.getAcountID(), this.input);
					
					if(hashmap.containsKey(false)) {
						int attempts_wrong = hashmap.values().stream().findFirst().get();
						painter.setErrorMsgVisible(true, screen, attempts_wrong);
						if(attempts_wrong == 3) {
							Thread.sleep(5000);
							painter.switchPane(ImgBackgrounds.FW1_1);
						}
					}
					else {
						painter.setAccount(painter.getQuery().getAcountInfo(painter.getAcountID()));
						painter.switchPane(ImgBackgrounds.FH1_1);
					}
					input = "";
					painter.setPassword(input);
				
				} catch (SQLException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case FP1_1:
			try {
				withdrawMoney();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
		
		}					
	}
	
	private void withdrawMoney() throws SQLException {
		var amount = Integer.parseInt(this.input);
	  	
	  	if(!checkIfLegitSum(amount)) {
	  		this.input = "";
	  		painter.setAmount("");
	  		return;
	  	}
	
	  	var d = new BigDecimal(amount);
		if (painter.getAcount().getBalance().compareTo(d) == 1 ) {
			painter.setAmount(Integer.toString(amount));
			painter.switchPane(ImgBackgrounds.FB1_1);
			this.input = "";
		} else {
			if(painter.getAmount() != null) painter.setAmount("");
			painter.setErrorMsgVisible(true, ImgBackgrounds.error, 0);
			this.input = "";
		}
	}

	/**
	 * If the input is not empty make a new input wich is one character smaller.
	 * if the parameter screen is FV1_1 set the displayed amount with the value input
	 * if screen is FL1_1 set the displayed password with the value input
	 * otherwise clear input 
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
	 * if the remainder of the parameter amount / 10 equals 0 return true
	 * otherwise return false
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
	 * changes the imgSelectors to
	 * 
	 * @param imgSelectorA 	is button A {@code ImgBackgrounds}
	 * @param imgSelectorB	is button B	{@code ImgBackgrounds}
	 * @param imgSelectorC	is button C {@code ImgBackgrounds}
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