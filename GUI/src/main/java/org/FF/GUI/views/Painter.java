package org.FF.GUI.views;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

import org.FF.GUI.common.Moneydispenser;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;

import java.util.ArrayList;  

public class Painter {
	
	private JFrame f;
	private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane p = new JLayeredPane();
	private DatabaseQueryClass query = new DatabaseQueryClass();
	private KeypadListener keypadSwitchScreenListener;
	private RFIDListener fRfidListener;
	private Acount acount;
	private Moneydispenser moneydispenser;
	private ImgBackgrounds currrentScreen;
	private JTextField saldo;
	private JTextField amount;
	private JTextField password;
	private JLabel errorMsgAmount;
	private JLabel errorMsgLogin;
	private int acountID;
	private JLabel banknotesTenOption;
	private JLabel banknotesTwentyOption;
	private JLabel banknotesFiftyOption;
	
	/**
	 * Sets up the begin frame and the keypadListener
	 * 
	 * @param serialConnection {@code SerialConnection}
	 * @param file 
	 */
	public Painter(ArrayList<SerialConnection> serialConnection, Moneydispenser moneydispenser) {
		this.keypadSwitchScreenListener = new KeypadListener(this, serialConnection, moneydispenser); 
		this.fRfidListener = new RFIDListener(this, serialConnection.get(1));
		this.acount = new Acount();
		this.moneydispenser = moneydispenser;
		this.f = new JFrame();
		this.f.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(true);
		this.f.setVisible(true);
		fRfidListener.start();
		keypadSwitchScreenListener.start();
		//this.f.setAlwaysOnTop (true);
	
		
		switchPane(ImgBackgrounds.FW1_1);
	}
		
	
	/**
	 * set the imgSelectors in every case.
	 * and if it is the saldo screen, the screen where you type in an amount or the login screen add an JTextField after the text.
	 * for the screen where you type in an amount or the login screen add a JLabel for the errorMessage.
	 * set the boundries for every component in the frame including the frame itself. 
	 * add the label to the pane and the pane to the frame. 
	 * and set the behavior for the frame.
	 * 
	 * @param img {@code ImgBackgrounds}
	 */
	public synchronized void switchPane(ImgBackgrounds img) {
		
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon(getClass().getResource("/"+ img+  ".png"));
		JLabel imageLabel = new JLabel("", bgIcon, SwingConstants.CENTER);
	
		
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		imageLabel.setBounds(0, 0, this.screensize.width, this.screensize.height);
		
		p2.add(imageLabel);

		this.currrentScreen = img;
		switch(img) {
		 	case FB1_1:
				//DONE	
		 		
				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, ImgBackgrounds.FW1_1, ImgBackgrounds.FW1_1, null);
				
		 		break;
			case FS1_1:
		 		this.saldo = new JTextField();
		 		this.saldo.setText(acount.getBalance().toString());
		 		this.saldo.setBounds(690, 470, 350, 60);
		 		this.saldo.setFont(new Font(null,Font.BOLD, 36));
		 		this.saldo.setColumns(10);
				
				saldo.setEditable(false);
				p2.add(saldo, JLayeredPane.POPUP_LAYER);
				//DONE																						
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FP1_1, null, ImgBackgrounds.FW1_1, null, null, null);
				break;
				
			case FV1_1:
		 		this.amount = new JTextField();
				this.amount.setBounds(830, 460, 350, 75);
				this.amount.setColumns(10);
				this.amount.setFont(new Font(null,Font.BOLD, 36));
				this.amount.setEditable(false);
				
				
				this.errorMsgAmount = new JLabel();
				this.errorMsgAmount.setBounds(300, 550, 660, 45);
				this.errorMsgAmount.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.errorMsgAmount.setForeground(Color.decode("#FF0000"));
				this.errorMsgAmount.setBackground(Color.decode("#CCCCCC"));
				this.errorMsgAmount.setVisible(false);
				this.errorMsgAmount.setOpaque(true);
				
				p2.add(this.amount, JLayeredPane.POPUP_LAYER);
				p2.add(errorMsgAmount, JLayeredPane.POPUP_LAYER);
				//DONE																											
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FP1_1, null, ImgBackgrounds.FW1_1, null, ImgBackgrounds.FB1_1, null);
			    break;
		 	case FH1_1:
		 		
		 		this.errorMsgAmount = new JLabel();
				this.errorMsgAmount.setBounds(350, 800, 660, 45);
				this.errorMsgAmount.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.errorMsgAmount.setForeground(Color.decode("#FF0000"));
				this.errorMsgAmount.setBackground(Color.decode("#CCCCCC"));
				this.errorMsgAmount.setVisible(false);
				this.errorMsgAmount.setOpaque(true);
		 		
				p2.add(errorMsgAmount, JLayeredPane.POPUP_LAYER);
		 		
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FP1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FW1_1, null, null, null);
				
			    break;
		 	case FP1_1:				
				
				this.errorMsgAmount = new JLabel();
				this.errorMsgAmount.setBounds(350, 800, 660, 45);
				this.errorMsgAmount.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.errorMsgAmount.setForeground(Color.decode("#FF0000"));
				this.errorMsgAmount.setBackground(Color.decode("#CCCCCC"));
				this.errorMsgAmount.setVisible(false);
				this.errorMsgAmount.setOpaque(true);
		 		
				p2.add(errorMsgAmount, JLayeredPane.POPUP_LAYER);
				
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FV1_1, null, ImgBackgrounds.FW1_1, null, null, ImgBackgrounds.FB1_1);
				break;
			case FW1_1:
				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, null, null, null);
				keypadSwitchScreenListener.suspendThread();
				fRfidListener.activateThread();
				break;
			case FL1_1:
				
				this.password = new JPasswordField();
		 		this.password.setText("");
				this.password.setBounds(980, 460, 350, 60);
				this.password.setFont(new Font(this.password.getFont().getName(),Font.BOLD, 36));
				this.password.setColumns(10);
				
				this.errorMsgLogin = new JLabel();
				this.errorMsgLogin.setBounds(450, 550, 1000, 45);
				this.errorMsgLogin.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.errorMsgLogin.setForeground(Color.decode("#FF0000"));
				this.errorMsgLogin.setBackground(Color.decode("#CCCCCC"));
				this.errorMsgLogin.setOpaque(true);
				this.errorMsgLogin.setVisible(false);
				
				this.password.setEditable(false);
				p2.add(this.password, JLayeredPane.POPUP_LAYER);
				p2.add(this.errorMsgLogin, JLayeredPane.POPUP_LAYER);

				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, null, ImgBackgrounds.FH1_1, null);

				fRfidListener.suspendThread();
				keypadSwitchScreenListener.activateThread();
				
				break;
			case FK1_1:

				this.banknotesTenOption = new JLabel();
				//this.banknotesTenOption.setText("kies zoveel mogelijk 10 ofzo (1)");
				this.banknotesTenOption.setBounds(350, 800, 660, 45);
				this.banknotesTenOption.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.banknotesTenOption.setForeground(Color.black);
				this.banknotesTenOption.setBackground(Color.decode("#FF0000"));
				this.banknotesTenOption.setVisible(true);
				this.banknotesTenOption.setOpaque(true);

				this.banknotesTwentyOption = new JLabel();
				//this.banknotesTwentyOption.setText("kies zoveel mogelijk 20 ofzo (2)");
				this.banknotesTwentyOption.setBounds(350, 870, 660, 45);
				this.banknotesTwentyOption.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.banknotesTwentyOption.setForeground(Color.black);
				this.banknotesTwentyOption.setBackground(Color.decode("#FF0000"));
				this.banknotesTwentyOption.setVisible(true);
				this.banknotesTwentyOption.setOpaque(true);

				this.banknotesFiftyOption = new JLabel();
				//this.banknotesFiftyOption.setText("kies zoveel mogelijk 50 ofzo (3)");
				this.banknotesFiftyOption.setBounds(350, 940, 660, 45);
				this.banknotesFiftyOption.setFont(new Font(this.errorMsgLogin.getFont().getName(),Font.BOLD, 36));
				this.banknotesFiftyOption.setForeground(Color.black);
				this.banknotesFiftyOption.setBackground(Color.decode("#FF0000"));
				this.banknotesFiftyOption.setVisible(true);
				this.banknotesFiftyOption.setOpaque(true);
				
				p2.add(this.banknotesTenOption, JLayeredPane.POPUP_LAYER);
				p2.add(this.banknotesTwentyOption, JLayeredPane.POPUP_LAYER);
				p2.add(this.banknotesFiftyOption, JLayeredPane.POPUP_LAYER);
				
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, null, null, ImgBackgrounds.FW1_1, null, ImgBackgrounds.FB1_1, null);
				break;

				
		}
		
		
		this.f.remove(p);
		this.p = p2;

		this.f.add(p);
		f.repaint();
	}

	public synchronized ImgBackgrounds getScreen() {
		return currrentScreen;
	}
	
	public synchronized String getAmount() {
		if(amount == null) return null;
		return this.amount.getText();
	}

	public synchronized void setAmount(String amount) {
		if(this.amount == null)return;
		this.amount.setText(amount);
	}

	public synchronized int getAcountID() {
		return this.acountID;
	}

	public synchronized void setAcountID(int acountID) {
		this.acountID = acountID;
	}

	public synchronized void setAccount(Acount acount) {
		this.acount = acount;
	}
	
	public synchronized String getPassword() {
		return this.password.getText();
	}

	public synchronized void setPassword(String password) {
		this.password.setText(password);
	}
	
	public synchronized DatabaseQueryClass getQuery() {
		return this.query;
	}
	
	public Acount getAcount() {
		return acount;
	}
	
	/**
	 * Check which screen is active. If it is the screen where you can type in an amount or the login screen 
	 * set the error message with the value of the parameter visible
	 * 
	 * @param visible
	 * @param currentScreen
	 */
	public synchronized void setErrorMsgVisible(boolean visible, ImgBackgrounds currentScreen, int attempts_wrong) {
		
		switch (currentScreen) {
			case FL1_1:
				
				this.errorMsgLogin.setText("Pincode incorrect probeer opnieuw nog " + (3-attempts_wrong) + " pogingen over.");
				this.errorMsgLogin.setVisible(visible);
				break;
				
			case FV1_1:
				this.errorMsgAmount.setText("Het bedrag is niet deelbaar door 10.");
				this.errorMsgAmount.setVisible(visible);
				break;
		default:
				this.errorMsgAmount.setText("Dit kan niet u heeft niet zoveel geld.");
				this.errorMsgAmount.setVisible(visible);
				break;
		}
	}


	public synchronized void setOptionsText(String amountBanknotesTen, String amountBanknotesTwenty, String amountBanknotesFifty) {
		this.banknotesTenOption.setText(amountBanknotesTen + " briefjes van 10");
		this.banknotesTwentyOption.setText(amountBanknotesTwenty + " briefjes van 20");
		this.banknotesFiftyOption.setText(amountBanknotesFifty + " briefjes van 50");

		this.banknotesTenOption.setVisible(true);
		this.banknotesTwentyOption.setVisible(true);
		this.banknotesFiftyOption.setVisible(true);
	}



}