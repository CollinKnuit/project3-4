package org.FF.GUI.app;


import org.FF.GUI.common.TestApp;
import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;
import org.FF.GUI.common.database.Password;
import org.FF.GUI.views.ImgBackgrounds;
import org.FF.GUI.views.Painter;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class App {   
	
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	private static Painter painter;
	
	public static void main(String args[]) throws IOException, SQLException {  
        // create a welcome screen.
        	
        EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				painter = new Painter(serialConnection);
    			} catch (Exception e) {
    				e.printStackTrace();
					
    			}
    		}
    	});

       }
        
   } 