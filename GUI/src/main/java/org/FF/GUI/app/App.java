package org.FF.GUI.app;


import org.FF.GUI.common.TestApp;
import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
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
	
	//private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	//private static Painter painter;
	
	public static void main(String args[]) throws IOException, SQLException {  
        // create a welcome screen.
        	
//        EventQueue.invokeLater(new Runnable() {
//    		public void run() {
//    			try {
//    				painter = new Painter(serialConnection.get(0));
//    			} catch (Exception e) {
//    				e.printStackTrace();
//    			}
//    		}
//    	});
        	
//		String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Europe/Amsterdam";
//		String username = "root";
//		String password = "L#go1706ue";
//
//		System.out.println("Connecting database...");
//	
//
//		try (Connection connection = DriverManager.getConnection(url, username, password)) {
//		    System.out.println("Database connected!");
//		    connection.close();
//		} catch (SQLException e) {
//		    throw new IllegalStateException("Cannot connect the database!", e);
//		}
		DatabaseQueryClass a = new DatabaseQueryClass();
		var d = a.getAcountInfo(3);
		System.out.println(d.getBalance());
		a.closeConnection();
       }
        
   } 