package org.FF.GUI.app;


import org.FF.GUI.common.FIleupdate;
import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.ConnectionClass;
import org.FF.GUI.common.database.DatabaseQueryClass;
import org.FF.GUI.common.database.Password;
import org.FF.GUI.views.ImgBackgrounds;
import org.FF.GUI.views.KeypadListener;
import org.FF.GUI.views.Painter;
import org.FF.GUI.views.RFIDListener;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class App {   
	
	private static FIleupdate file;
	private static ArrayList<SerialConnection> serialConnection;
	
	private static Painter painter;
	
	public static void main(String args[]) throws IOException, SQLException {  

		file = new FIleupdate();
		var a = file.getData();
		
		if(a[0].contains("null") || a[1].contains("null")) {
			serialConnection = new ComPortSetup(a).setupComPort();
			file.updateFile();
		}else {
			serialConnection = new ComPortSetup(a).getPorts();
		}
		
		System.out.println();
		
        // create a welcome screen.
       
        EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				painter = new Painter(serialConnection, file);
    			} catch (Exception e) {
    				e.printStackTrace();
					
    			}
    		}
    	});
	

       }
        
   } 