package org.FF.GUI.views;
import java.awt.*;  
import javax.swing.*;  

public class Painter {
	
	JFrame f;
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	JLayeredPane p = new JLayeredPane();
	
	Painter(String img){
		/* instantiate the JFrame, the background image
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
		this.f.add(p);
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.f.getContentPane().setLayout(null);
		this.f.setUndecorated(true);
		this.f.setVisible(true);
	}
	
	public void switchPane(String img) {
		/* instantiate the new pane, background image and label
		 * remove the old pane and add the new pane.
		 */
		JLayeredPane p2 = new JLayeredPane();
		ImageIcon bgIcon = new ImageIcon("C:\\Users\\frank\\OneDrive\\Afbeeldingen\\" + img + ".png");
		JLabel l = new JLabel("", bgIcon, SwingConstants.CENTER);
		p2.setBounds(0, 0, this.screensize.width, this.screensize.height);
		l.setBounds(0, 0, this.screensize.width, this.screensize.height);
		p2.add(l);
		this.f.remove(p);
		this.p = p2;
		this.f.add(p2);
	}
}