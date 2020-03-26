package org.FF.GUI.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.FF.GUI.common.ComPortSetup;
import org.FF.GUI.common.SerialConnection;  

public class Painter {
	
	private JFrame f;
	private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane p = new JLayeredPane();
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	private Timer timer;
	private String img = "";
	
	private ActionListener keypadSwitchScreenListener = new ActionListener() {
		
		private String img;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// gets the input from the keypad
			var commands = serialConnection.get(0).getWaitingQueue();
			System.out.println(this.img);
			// for each input loop it
			for(var c: commands) {	
				
				switch(c) {
				  
				  case "A":
					  if(this.img.contentEquals("FH1_1")) {
						  switchPane("FB1_1");
					  } else if(this.img.contentEquals("FP1_1") || this.img.contentEquals("FS1_1") || this.img.contentEquals("FV1_1")) {
						  switchPane("FH1_1");
					  }
					  break;
				  case "B":
					  if(this.img.contentEquals("FH1_1")) {
						  switchPane("FP1_1");
					  } else if(this.img.contentEquals("FP1_1")) {
						  switchPane("FV1_1");
					  } else if(this.img.contentEquals("FV1_1")) {
						  switchPane("FP1_1");
					  } else if(this.img.contentEquals("FS1_1")) {
						  switchPane("FP_1");
					  }
					  break;
				  case "C":
					  if(this.img.contentEquals("FH1_1")) {
						  switchPane("FS1_1");
					  }
					  break;
				  case "D":
					  if(this.img.contentEquals("FH1_1")) {
						  switchPane("FW1_1");
					  } else if(this.img.contentEquals("FP1_1") || this.img.contentEquals("FS1_1") || this.img.contentEquals("FV1_1")) {
						  switchPane("FH1_1");
					  }
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
		
		public void setImg(String img) {
			this.img = img;
		}
	};
	
	Painter(String img){ 
		/* instantiate the JFrame, the background image 
		 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
		 * set the boundries for every component in the frame including the frame itself. 
		 * add the label to the pane and the pane to the frame. 
		 * and set the behavior for the frame. 
		 */ 
		this.f = new JFrame();
		ImageIcon bgIcon = new ImageIcon(this.getClass().getResource("/resources/" + img + ".png"));
		JLabel l = new JLabel("", bgIcon, SwingConstants.CENTER);
		JTextField listenerObject = new JTextField();
		
		listenerObject.setBounds(0, 0, 0, 0);
		listenerObject.setVisible(false);
		listenerObject.addActionListener(keypadSwitchScreenListener);
		timer = new Timer(10, keypadSwitchScreenListener);
		timer.start();
		
		this.f.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.p.setBounds(0, 0, this.screensize.width, this.screensize.height);
		l.setBounds(0, 0, this.screensize.width, this.screensize.height);
		
		this.p.add(l);
		if(img.contentEquals("FS1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(690, 470, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		
		if(img.contentEquals("FV1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(770, 460, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		this.p.add(listenerObject);
		this.f.add(p);
		
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(true);
		this.f.setVisible(true);
	}
	
	public void switchPane(String img) {
		/* instantiate the new pane, background image and label 
		 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
		 * remove the old pane and add the new pane. 
		 */
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon(this.getClass().getResource("/resources/" + img + ".png"));
		JLabel imageLabel = new JLabel("", bgIcon, SwingConstants.CENTER);
		JTextField listenerObject = new JTextField();
		
		listenerObject.setBounds(0, 0, 0, 0);
		listenerObject.setVisible(false);
		listenerObject.addActionListener(keypadSwitchScreenListener);
		timer = new Timer(10, keypadSwitchScreenListener);
		timer.start();
		
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		imageLabel.setBounds(0, 0, this.screensize.width, this.screensize.height);
		
		p2.add(imageLabel);
		p2.add(listenerObject);
		
		if(img.equals("FS1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(690, 470, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		
		if(img.equals("FV1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(740, 460, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		
		this.f.remove(p);
		this.p = p2;
		this.f.add(p2);
	}
}