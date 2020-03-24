package org.FF.GUI.common;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.Timer;

import java.awt.BorderLayout;

public class TestApp {

	
	private JFrame frame;
	
	///////////////////////////////////////////////////
	// This content should be copied
	private JPasswordField passwordField;
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	private String password = "1234";
	private String password2 = "";
	private Timer timer;
	
	// A action listener for the keypad
	private ActionListener keypadListener = new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// gets the input from the keypad
			var commands = serialConnection.get(0).getWaitingQueue();
			
			// for each input loop it
			for(var c: commands) {	
				
				switch(c) {
				  case "#": // if its a # check if the password is correct
					timer.stop();
					checkPassword();
				    break;
				    
				  case "*":// if its a * remove the last digit
					  if(password2.length() == 1) {
						  password2 = "";
						  
					  }else if(password2.length() >1){
						  password2 = password2.substring(0, password2.length()-1); 
					  }	
					  passwordField.setText(password2);
				    break;
				    
				  default:// if its not null add it to the password2 string
					 if(c != null ) {
						password2 += c;
						passwordField.setText(password2);
					}
				}
			}
		}
	};
	
	/**
	 * check if the password is the same and if it is exit system else it sets the passwordField to zero
	 */
	private void checkPassword() {
		if(password.contentEquals(password2)) {
			  System.exit(0); 
		}else{
			passwordField.setText("");
			password2 = "";
			
		}
	}
	
	///////////////////////////////////////////////////

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestApp window = new TestApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	


	/**
	 * Create the application.
	 */
	public TestApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		///////////////////////////////////////////////////
		passwordField = new JPasswordField();
		passwordField.addActionListener(keypadListener);
		timer = new Timer(10, keypadListener);
		timer.start();
		///////////////////////////////////////////////////
		
		
		frame.getContentPane().add(passwordField, BorderLayout.CENTER);
	
	}

}
