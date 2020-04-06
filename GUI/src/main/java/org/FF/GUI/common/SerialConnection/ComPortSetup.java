package org.FF.GUI.common.SerialConnection;

import java.util.ArrayList;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class ComPortSetup{
	
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<String> portNames;

	public ComPortSetup() {
		this.portNames = getAllPortNames();
	}
	
	/**
	 * The first index is the keypad
	 * The second index is the rfid
	 * The third index is the dispenser
	 * The fourth index is the bonprinter
	 * @return an array with all comports
	 *
	 */
	public ArrayList<SerialConnection> setupComPort() {
		if(portNames.isEmpty()) {
			return null;
		}
		
		System.out.println("Do you want to setup the ports(y/n)");
		if(!userinput()) {
			return null;
			
		}
		
		System.out.println();
		
		
		
		if (!availablePorts()) {
			System.out.println("Er zijn geen ports available.");
			System.out.println("Attach een port op de pc");
			System.out.println("De program gaat nu stoppen");
			scanner.close();
			System.exit(0) ;
			
		}
		
		System.out.println();
		
		var serialPorts = new ArrayList<SerialConnection>();
		var serial = new SerialConnection();
		

		serial = setup(115200, "Do you want to skip setup for the keypad(y/n)");
			
		if (serial != null) {
			serial.addPortListener();
			serial.openPort();
			serialPorts.add(serial);
		}else {
			serialPorts.add(null);
		}	
		
		
		availablePorts();
		
		serial = setup(9600, "Do you want to skip setup for the rfid(y/n)");
		
		if (serial != null) {
			serial.openPort();
			serialPorts.add(serial);
		}else {
			serialPorts.add(null);
		}

		
		System.out.println();
		
		availablePorts();
		
		serial = setup(9600, "Do you want to skip setup for the dispenser(y/n)");
		
		if (serial != null) {
			serial.openPort(); 
			serialPorts.add(serial);
			
		}else {
			serialPorts.add(null);
		}
		
	
		serial = setup(9600, "Do you want to skip setup for the bonPrinter(y/n)");
		
		if (serial != null) {
			serial.openPort();
			serialPorts.add(serial);
			
		}else {
			serialPorts.add(null);
		}
		
		
		return serialPorts;
		
	}
	
	/**
	 * Sets up the SerialConnection 
	 * @param boudRate and String to display
	 * @return
	 */
	private SerialConnection setup(int boudRate, String sysoutString) {
		if (portNames.isEmpty()) return null;
		
		System.out.println(sysoutString);
		
		if(!userinput()) {
			var a = portComToUse();
			var serial = new SerialConnection(a, boudRate);
			portNames.remove(a);
			serial.openPort();
			return serial;
		
		}else {
			return null;
			
		}
	}
	
	/**
	 * Checks if there are ports
	 * @return boolean
	 */
	private boolean availablePorts() {
		if (portNames.isEmpty()) return false;
		
		System.out.println("Deze ports zijn er");
		
		for(int i = 0; i < portNames.size();i++) {
			System.out.println(i + ": " + portNames.get(i));
			
		}
		return true;
	}
	
	/**
	 * Waits for user input
	 * @return boolean
	 */
	private boolean userinput() {
		while(true) {
			var userInput = scanner.nextLine();
			
			if(userInput.contentEquals("y")) {
				return true;
				
			}else if(userInput.contentEquals("n")) {
				
				return false;
			}
		}
	}

	/**
	 * selects comport to use
	 * @return String
	 */
	private String portComToUse() {
		System.out.println("Welke port wilt u gebruiken(input id)");
		while(true) {
			var userInput =  scanner.nextLine();
			try {
				var a = Integer.parseInt(userInput);
				var b = portNames.get(a);
				return b;
				
			} catch (NumberFormatException  e) {
				System.out.println("Die id bestaat niet");
			}
		}	
	}
	
	/**
	 * gets all portnames
	 * @return ArrayList<String> of portnames
	 */
	private ArrayList<String> getAllPortNames() {
		SerialPort[] ports = SerialPort.getCommPorts();
		if (ports.length == 0) {
			System.out.println("No serial ports available!");
			return null;
		}

		var array = new ArrayList<String>();
		for (int i = 0; i < ports.length; i++) {
			array.add(ports[i].getSystemPortName());
		}

		return array;
	}
}
