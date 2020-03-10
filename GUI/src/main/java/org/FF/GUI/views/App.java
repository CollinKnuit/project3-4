package org.FF.GUI.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class App {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
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
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 154, 433, 261);
		
		JLabel lblZdsv = new JLabel("zdsv");
		lblZdsv.setBounds(92, 77, 22, 14);
		
		JLabel welcomeText = new JLabel("Willkommen bei die FF");
		welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("C:\\Users\\frank\\Downloads\\germany-flag-icon-128.png"));
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\Users\\frank\\Downloads\\iconfinder_atm-1_3338908.png"));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(151)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(welcomeText, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
								.addComponent(label)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(92)
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 266, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(75, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(34)
					.addComponent(label)
					.addGap(32)
					.addComponent(welcomeText, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_1)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblZdsv);
		frame.getContentPane().add(panel);
	}
}
