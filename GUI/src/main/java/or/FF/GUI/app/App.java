package or.FF.GUI.app;
import java.awt.*;  
import javax.swing.*;

import org.FF.GUI.common.ComPortSetup;
import org.FF.GUI.common.SerialConnection;
import org.FF.GUI.views.Painter;

import java.util.ArrayList;
import java.util.Scanner;
public class App {   
	
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	
        public static void main(String args[]) {  
        	// create a welcome screen.
        	Painter p = new Painter(serialConnection.get(0));
        	
      
        
        
        
    } 