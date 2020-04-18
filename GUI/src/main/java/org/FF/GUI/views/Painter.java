package org.FF.GUI.views;

import java.awt.*;

import javax.swing.*;

import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import java.util.ArrayList;  

public class Painter {
	
	private JFrame f;
	private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane p = new JLayeredPane();
	private JTextField saldo;
	private Timer timer;
	private DatabaseQueryClass query = new DatabaseQueryClass();
	private KeypadListener keypadSwitchScreenListener;
	private Acount acount;
	private ImgBackgrounds currrentScreen;
	private JTextField amount;
	private JTextField password;
	private int acountID;
	
	/**
	 * Sets up the begin frame and the keypadListener
	 * @param serialConnection {@code SerialConnection}
	 */
	public Painter(ArrayList<SerialConnection> serialConnection) {
		this.keypadSwitchScreenListener = new KeypadListener(this, serialConnection); 
		this.timer = new Timer(10, keypadSwitchScreenListener);
		
		this.f = new JFrame();
		this.f.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(true);
		this.f.setVisible(true);
		this.f.setAlwaysOnTop (true);
		
		switchPane(ImgBackgrounds.FH1_1);
		this.currrentScreen = "FH1_1";
	}
		
	
	/**
	 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
	 * set the boundries for every component in the frame including the frame itself. 
	 * add the label to the pane and the pane to the frame. 
	 * and set the behavior for the frame.
	 * @param img {@code ImgBackgrounds}
	 */
	public synchronized void switchPane(ImgBackgrounds img) {
		timer.stop();
		
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon(getClass().getResource("/"+ img+  ".png"));
		JLabel imageLabel = new JLabel("", bgIcon, SwingConstants.CENTER);
		JTextField listenerObject = new JTextField();
	
		listenerObject.setBounds(0, 0, 0, 0);
		listenerObject.setVisible(false);
		listenerObject.addActionListener(keypadSwitchScreenListener);
		
		
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		imageLabel.setBounds(0, 0, this.screensize.width, this.screensize.height);
		
		p2.add(imageLabel);
		p2.add(listenerObject);

		this.currrentScreen = ImgBackgrounds.img;
		switch(img) {
		 	case FB1_1:
				 // code block
				//DONE	
		 		
				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, ImgBackgrounds.FW1_1, ImgBackgrounds.FW1_1, null);
				
		 		break;
			case FS1_1:
	
		 		saldo = new JTextField();
		 		saldo.setText(acount.getBalance().toString());
				saldo.setBounds(690, 470, 350, 60);
				saldo.setColumns(10);
				
				saldo.setEditable(false);
				p2.add(saldo, JLayeredPane.POPUP_LAYER);
				//DONE																						//afbreken moet nog
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FP1_1, null, ImgBackgrounds.FW1_1, null, null, null);
				break;
				
			case FV1_1:
		 		this.amount = new JTextField();
				this.amount.setBounds(740, 460, 350, 60);
				this.amount.setColumns(10);
				this.amount.setEditable(false);
				p2.add(this.amount, JLayeredPane.POPUP_LAYER);
				//DONE																											
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FP1_1, null, ImgBackgrounds.FW1_1, null, ImgBackgrounds.FB1_1, null);
			    break;
		 	case FH1_1:
		 		//DONE																										  AFBREKEN MOET NOG
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FP1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FW1_1, null, null, null);

			    break;
		 	case FP1_1:
		 		
		 		//DONE																						  AFBREKEN MOET NOG
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FH1_1, ImgBackgrounds.FV1_1, null, ImgBackgrounds.FW1_1, null, null, ImgBackgrounds.FB1_1);
				break;
			case FW1_1:
				//DONE
				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, null, null, null);
			case FL1_1:

				this.password = new JPasswordField();
		 		this.password.setText("");
				this.password.setBounds(690, 470, 350, 60);
				this.password.setColumns(10);
				
				this.password.setEditable(false);
				p2.add(this.password, JLayeredPane.POPUP_LAYER);

				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, null, ImgBackgrounds.FH1_1, null);

				break;

				
		}
		
		
		this.f.remove(p);
		this.p = p2;

		this.f.add(p);
		f.repaint();
		timer.start();
	}

	// returns a string wich represents wich backgroundImage is active
	public synchronized ImgBackgrounds getScreen() {
		return currrentScreen;
	}

	// return a string wich contains the amount of money the user wants to withdraw
	public synchronized String getAmount() {
		return this.amount.getText();
	}

	public synchronized void setAmount(String amount) {
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
	

	
}