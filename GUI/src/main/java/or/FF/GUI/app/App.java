package or.FF.GUI.app;


import org.FF.GUI.common.ComPortSetup;
import org.FF.GUI.common.SerialConnection;
import org.FF.GUI.views.ImgBackgrounds;
import org.FF.GUI.views.Painter;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class App {   
	
	private static ArrayList<SerialConnection> serialConnection = new ComPortSetup().setupComPort(); // gets all comm ports
	
        public static void main(String args[]) throws IOException {  
        	// create a welcome screen.
        	Painter p = new Painter(serialConnection.get(0));
        	
        }
        
    } 