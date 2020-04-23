package org.FF.GUI.views;

import java.awt.*;

import javax.swing.*;

import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;

import java.util.ArrayList;  

public class Painter {
	
	private JFrame f;
	private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane p = new JLayeredPane();
	private JTextField saldo;
	private DatabaseQueryClass query = new DatabaseQueryClass();
	private KeypadListener keypadSwitchScreenListener;
	private RFIDListener fRfidListener;
	private Acount acount;
	private ImgBackgrounds currrentScreen;
	private JTextField amount;
	private JTextField password;
	private int acountID;
	
	/**
	 * Sets up the begin frame and the keypadListener
	 * 
	 * @param serialConnection {@code SerialConnection}
	 */
	public Painter(ArrayList<SerialConnection> serialConnection) {
		this.keypadSwitchScreenListener = new KeypadListener(this, serialConnection); 
		this.fRfidListener = new RFIDListener(this, serialConnection.get(1));
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
	 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
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
	
		 		saldo = new JTextField();
		 		saldo.setText(acount.getBalance().toString());
				saldo.setBounds(690, 470, 350, 60);
				saldo.setColumns(10);
				
				saldo.setEditable(false);
				p2.add(saldo, JLayeredPane.POPUP_LAYER);
				//DONE																						
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
		 		//																							  
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FP1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FW1_1, null, null, null);
				
			    break;
		 	case FP1_1:
		 		
		 		//DONE																						
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
				this.password.setBounds(690, 470, 350, 60);
				this.password.setColumns(10);
				
				this.password.setEditable(false);
				p2.add(this.password, JLayeredPane.POPUP_LAYER);

				keypadSwitchScreenListener.setImgSelectors(null, null, null, null, null, ImgBackgrounds.FH1_1, null);

				fRfidListener.suspendThread();
				keypadSwitchScreenListener.activateThread();
				
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
	
	public Acount getAcount() {
		return acount;
	}
	

	
}