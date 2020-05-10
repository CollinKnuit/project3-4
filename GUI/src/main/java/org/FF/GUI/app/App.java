package org.FF.GUI.app;

import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.FF.GUI.common.FileUpdate;
import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.views.Painter;

public class App {   
	
	private static FileUpdate file;
	private static ArrayList<SerialConnection> serialConnection;
	
	private static Painter painter;
	
	public static void main(String args[]) throws IOException, SQLException {  

		file = new FileUpdate();
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