package org.FF.GUI.views;

import java.awt.*;

import javax.swing.*;

import org.FF.GUI.common.SerialConnection;  

public class Painter {
	
	private JFrame f;
	private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private JLayeredPane p = new JLayeredPane();
	private JTextField saldo;
	private Timer timer;
	private KeypadListener keypadSwitchScreenListener;
		
	
	public Painter(SerialConnection serialConnection) {
		this.keypadSwitchScreenListener = new KeypadListener(this, serialConnection); 
		this.timer = new Timer(10, keypadSwitchScreenListener);
		
		this.f = new JFrame();
		this.f.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(true);
		this.f.setVisible(true);
		
		switchPane(ImgBackgrounds.FH1_1);
	}
		
	
	/**
	 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
	 * set the boundries for every component in the frame including the frame itself. 
	 * add the label to the pane and the pane to the frame. 
	 * and set the behavior for the frame.
	 * @param img {@code ImgBackgrounds}
	 */
	public void switchPane(ImgBackgrounds img) {
		timer.stop();
		
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon(this.getClass().getResource("/resources/" + img + ".png"));
		JLabel imageLabel = new JLabel("", bgIcon, SwingConstants.CENTER);
		JTextField listenerObject = new JTextField();
		
		listenerObject.setBounds(0, 0, 0, 0);
		listenerObject.setVisible(false);
		listenerObject.addActionListener(keypadSwitchScreenListener);
		
		
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		imageLabel.setBounds(0, 0, this.screensize.width, this.screensize.height);
		
		p2.add(imageLabel);
		p2.add(listenerObject);
		
		switch(img) {
		 	case FB1_1:
		 		// code block
		 		
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1);
		 		break;
		 	case FP1_1:
		 		// code block
		 		
		 		
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1);
		 		break;
		 	case FS1_1:
		 		saldo = new JTextField();
				saldo.setBounds(690, 470, 350, 60);
				saldo.setColumns(10);
				saldo.setEditable(false);
				this.p.add(saldo);
				
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FV1_1, ImgBackgrounds.FV1_1, ImgBackgrounds.FV1_1, ImgBackgrounds.FV1_1);
			    break;
		 	case FV1_1:
		 		saldo = new JTextField();
				saldo.setBounds(740, 460, 350, 60);
				saldo.setColumns(10);
				saldo.setEditable(false);
				this.p.add(saldo);
				
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1, ImgBackgrounds.FS1_1);
			    break;
		 	case FH1_1:
			 
		 		
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1);

			    break;
		 	case FP_1:
		 		
		 		
				keypadSwitchScreenListener.setImgSelectors(ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1, ImgBackgrounds.FB1_1);
		 		break;
		}
		
		
		this.f.remove(p);
		this.p = p2;
		this.f.add(p);
		f.repaint();
		p.repaint();
		timer.start();
	}
	

	
}