package org.FF.GUI.app;


import org.FF.GUI.common.ComPortSetup;
import org.FF.GUI.common.SerialConnection;
import org.FF.GUI.common.TestApp;
import org.FF.GUI.views.ImgBackgrounds;
import org.FF.GUI.views.Painter;

import java.awt.EventQueue;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class App {   
	
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	private static Painter painter;
	
	public static void main(String args[]) throws IOException {  
        // create a welcome screen.
        	
        EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				painter = new Painter(serialConnection.get(0));
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});
        	
        
        while(true) {
        	System.out.println("ioh");
        }
       }
        
   } 