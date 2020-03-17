package org.FF.GUI.views;
import java.awt.*;  
import javax.swing.*;  

public class Painter {
	
	JFrame f;
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	JLayeredPane p = new JLayeredPane();
	
	Painter(String img){ 
		/* instantiate the JFrame, the background image 
		 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
		 * set the boundries for every component in the frame including the frame itself. 
		 * add the label to the pane and the pane to the frame. 
		 * and set the behavior for the frame. 
		 */ 
		this.f = new JFrame();
		ImageIcon bgIcon = new ImageIcon("C:\\Users\\frank\\OneDrive\\Afbeeldingen\\" + img + ".png");
		JLabel l = new JLabel("", bgIcon, SwingConstants.CENTER);
		this.f.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.p.setBounds(0, 0, this.screensize.width, this.screensize.height);
		l.setBounds(0, 0, this.screensize.width, this.screensize.height);
		this.p.add(l);
		if(img.equals("FS1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(690, 470, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		
		if(img.equals("FV1_1")) {
			JTextField saldo = new JTextField();
			saldo.setBounds(770, 460, 350, 60);
			saldo.setColumns(10);
			saldo.setEditable(false);
			this.p.add(saldo);
		}
		
		this.f.add(p);
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(false);
		this.f.setVisible(true);
	}
	
	public void switchPane(String img) {
		/* instantiate the new pane, background image and label 
		 * and if it is the saldo screen or the screen where you type in an amount add an JTextField after the text.
		 * remove the old pane and add the new pane. 
		 */
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon("C:\\Users\\frank\\OneDrive\\Afbeeldingen\\" + img + ".png");
		JLabel l = new JLabel("", bgIcon, SwingConstants.CENTER);
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		l.setBounds(0, 0, this.screensize.width, this.screensize.height);
		p2.add(l);
		
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