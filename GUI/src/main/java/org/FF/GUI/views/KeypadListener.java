package org.FF.GUI.views;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   

import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.config.Moneydispenser;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;

import java.util.ArrayList;
import java.util.Date;
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
	private SerialConnection serialConnectionDispenser;
	private AtomicBoolean suspend = new AtomicBoolean(true);
	private String input = "";
	private Moneydispenser moneydispenser;
	private CalculateBanknotes calc;
	private int bedrag;
	private Acount acount;
	private static int transactionNumber = 999;
	
	/**
	 * 
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */
	public KeypadListener(Painter painter, ArrayList<SerialConnection> serialConnection, Moneydispenser moneydispenser) {
		this.painter = painter;
		this.serialConnectionKeypad = serialConnection.get(0);
		this.serialConnectionDispenser = serialConnection.get(2);
		this.serialConnectionBonprinter = serialConnection.get(3);
		this.moneydispenser = moneydispenser;
		this.calc = new CalculateBanknotes();
	}
	

	/**
	 * suspend the thread
	 */
	public void suspendThread() {
		synchronized (this) {
			serialConnectionKeypad.removePortListener();
			suspend.compareAndSet(false, true);		
			input = "";
			bedrag = 0;
		}
	}
	
	/**
	 * activate the thread
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
							
							if(imgSelectorA == ImgBackgrounds.FB1_1) {
								input = "70";
								try {
									withdrawMoney(screen);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							} else {
								painter.switchPane(imgSelectorA);
							}
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
			  	  			painter.switchPane(imgSelectorS);	
			  	  		} 
			  	  		else{
			  	  			backspace(screen);
			  	  		}

			  	  		break;
				  	
					case "#":
						if(screen == ImgBackgrounds.FB1_1){
							printBon(bedrag);
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

						if(screen == ImgBackgrounds.FK1_1) {
							
							try {
								chooseBankNotes(c);
							} catch (SQLException | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
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
	 * stops thread
	 * @return
	 */
	public boolean stopThread() {
		return exit.compareAndSet(false, true);
	}
	
	/**
	 * starts thread
	 * @return
	 */
	public boolean startThread() {
		return exit.compareAndSet(true, false);
	}
		
	/**
	 * depending on the parameter a (amount) set the input(the amount of money that wants to be printed) to 10, 20, 50, 100.
	 * and execute enter(see enter comments for a description)
	 * 
	 * @param a (amount)
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
	 * if the parameter screen equals FV1_1 execute withdrawMoney(see comments withdrawMoney for description)
	 * 
	 * if the parameter screen equals FL1_1 check if input is a pair with acountID (from acount) in hasmap. If so request acountInfo 
	 * from acount out of the database and update acount with the values from the query in the method getAcountInfo and continue to the homescreen
	 * and after that clear input and the displayed password.
	 * if false calculate attempts_wrong and set the error message that belongs to that error.
	 * if attemps_wrong is 3 sleep for 5 seconds and return to the welcomeScreen
	 *  
	 * to see how setErrorMsgVisible works see comments setErrorMsgVisible in painter.
	 * 
	 * if screen is FP1_1 try to execute withdraw money if SQLException received print stackTrace.
	 * if screen is not FP1_1, FV1_1 or FL1_1 do nothing.
	 * @param screen
	 * @throws SQLException 
	 * 
	 * @return
	 */
	private void enter(ImgBackgrounds screen)  {
		if(input == "") return;
		
		switch(screen) {
		  	case FV1_1:
		  		try {
		  			withdrawMoney(screen);
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
						painter.setErrorMsgVisible(true, screen, attempts_wrong, false);
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
					withdrawMoney(screen);
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
	 * if checkIfLegitSum equals false check if amount is between 0 and 250. 
	 * if so display the error message that corresponds with that error.
	 * if not display the error message that says amount is not a multiple of 10.
	 * after that clear amount on the GUI and input. And return.
	 * 
	 *  if checkIfLegitSum equals true check if the user has enough money on the bank
	 *  if so store input in bedrag as string display amount on the screen and clear input
	 *  after that go the FK1_1(see ImgBackgrounds for description)
	 *  if the user does not have enough money on the bank clear input. 
	 *  And display the corresponding error message.
	 * 
	 * @param screen
	 * @throws SQLException
	 */
	private void withdrawMoney(ImgBackgrounds screen) throws SQLException {
		var amount = Integer.parseInt(this.input);
	  	
	  	if(!checkIfLegitSum(amount)) {
	  		if(amount > 250 || amount == 0) { 
	  			painter.setErrorMsgVisible(true, ImgBackgrounds.FV1_1, 0, true);
	  		}else {
	  			painter.setErrorMsgVisible(true, ImgBackgrounds.FV1_1, 0, false);
	  		}
	  		this.input = "";
	  		painter.setAmount("");
	  		return;
	  	}
	
	  	var d = new BigDecimal(amount);
	  	var  e = painter.getAcount().getBalance().compareTo(d);
		if (e == 1 || e == 0 ) {
			painter.setAmount(Integer.toString(amount));
			bedrag = Integer.parseInt(input);
			this.input = "";
			painter.switchPane(ImgBackgrounds.FK1_1);
			
		} else {
			if(painter.getAmount() != null) painter.setAmount("");
			painter.setErrorMsgVisible(true, ImgBackgrounds.error, 0, false);
			this.input = "";
		}
	}

	/**
	 * If the input is not empty make a new input wich is one character smaller.
	 * if the parameter screen is FV1_1 set the displayed amount with the value input
	 * if screen is FL1_1 set the displayed password with the value input
	 * if input length is 0 characters clear input 
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
	 * if the remainder of the parameter amount / 10 does not equal 0 return false 
	 * and if amount is 0 or greater than 250 return false
	 * otherwise return true
	 * 
	 * @param amount
	 * @return true/false
	 */
	private synchronized Boolean checkIfLegitSum(int amount) {	
		if(amount > 250 || amount == 0) return false;
		
		int remainder = Math.abs(amount % 10);
		if(remainder != 0) {
			return false;
		}
		 
		return true;
	}

	/**
	 * initialize the variables that represent date, pinAmount, rfidNumber, transactionNumber and dateSent
	 * and send those variables in one go the bonPrinter through that serialConnection.
	 * after that switch to the screen that corresponds with imgSelectorH.
	 * 
	 * @param input
	 */
	private void printBon(int input){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    	Date date = new Date();   
		String pinAmount = Integer.toString(input);
		
		if(input < 99) {
			pinAmount += ' ';
		}
		
		String rfidNumber = painter.getAcount().getRfidNumber();
		String transactionNumber = Integer.toString(++this.transactionNumber);
		String dateSent = formatter.format(date);
		this.serialConnectionBonprinter.sendData(pinAmount + rfidNumber + transactionNumber + dateSent);
		painter.switchPane(imgSelectorH);	

	}
	
	public SerialConnection getSerialConnectionKeypad() {
		return serialConnectionKeypad;
	}
		
	/**
	 * Initialize previusBanknotes10, previusBanknotes20 and previusBanknotes50 with the data that is stored in moneydispenser.
	 * fill array with the amount of banknotes needed to be printed of each type.
	 * 
	 * execute withDrawMoney query and if it returns false return
	 * otherwise update the amount of banknotes there are in each dispenser.
	 * convert array to a string and send that to the dispenser.
	 * 
	 * then check if there is a serialConnection with bonPrinter
	 * go to the welcome screen otherwise go to FB1_1
	 * 
	 * @param a (amount)
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void chooseBankNotes(String a) throws SQLException, IOException {
		int previusBanknotes10 = moneydispenser.getBanknotes_10();
		int previusBanknotes20 = moneydispenser.getBanknotes_20();
		int previusBanknotes50 = moneydispenser.getBanknotes_50();
		
		int array[] = calc.calculateBanknotesTotaal(bedrag, Integer.parseInt(a), previusBanknotes10, 
													previusBanknotes20, previusBanknotes50);
		
		if(!painter.getQuery().withDrawMoney(painter.getAcountID(), bedrag)) {
				return;
		}
	
		moneydispenser.updateBanknotes_10(array[0]);			
		moneydispenser.updateBanknotes_20(array[1]);
		moneydispenser.updateBanknotes_50(array[2]);
		
		moneydispenser.updateConfig();
		
		String amountOfBanknotes = Integer.toString(array[0]) + Integer.toString(array[1]) + Integer.toString(array[2]);
		serialConnectionDispenser.sendData(amountOfBanknotes);
		
		if(this.serialConnectionBonprinter == null) {
			painter.switchPane(ImgBackgrounds.FW1_1);
			return;
		}
		painter.switchPane(ImgBackgrounds.FB1_1);
	}
	
	public int getBedrag() {
		return bedrag;
	}


	/**
	 * changes the imgSelectors to
	 * 
	 * @param imgSelectorA 	is button A {@code ImgBackgrounds}
	 * @param imgSelectorB	is button B	{@code ImgBackgrounds}
	 * @param imgSelectorC	is button C {@code ImgBackgrounds}
	 * @param imgSelectorD	is button D	{@code ImgBackgrounds}
     * @param imgSelectorS	is button star {@code ImgBackgrounds}
	 * @param imgSelectorH	is button hashtag {@code ImgBackgrounds}
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