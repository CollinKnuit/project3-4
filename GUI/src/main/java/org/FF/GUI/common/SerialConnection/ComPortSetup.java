package org.FF.GUI.common.SerialConnection;

import java.util.ArrayList;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class ComPortSetup{
	
	private Scanner scanner = new Scanner(System.in);
	private ArrayList<String> portNames;
	private String[] config;
	
	public ComPortSetup(String[] config) {
		this.portNames = getAllPortNames();
		this.config = config;
	}
	
	
	/**
	 * If there is nothing or less than 2 items in PortNames exit and return null.
	 * If there are no ports available exit and return null.
	 * 
	 * If the user does not want to skip a setup of a serialConnection execute setup. 
	 * And check if serial is not null, if so open a port with serial and add serial to serialPorts
	 * If serial is null add null to serialPorts.
	 * 
	 * And to this for every serialConnection
	 * 
	 * Index 0 is the keypad
	 * Index 1 is the rfid
	 * Index 2 is the dispenser
	 * Index 3 is the bonprinter
	 * @return an array with all comports
	 *
	 */
	public ArrayList<SerialConnection> setupComPort() {
		
		
		if(portNames.isEmpty() || portNames.size() < 2) {
			System.exit(0); 
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
			System.out.println("Het programma gaat nu stoppen");
			scanner.close();
			System.exit(0) ;
			
		}
		
		System.out.println();
		
		var serialPorts = new ArrayList<SerialConnection>();
		var serial = new SerialConnection();
		

		serial = setup(115200, "Do you want to skip setup for the keypad(y/n)", 0);
			
		if (serial != null) {
			serial.openPort();
			serialPorts.add(serial);
		}else {
			serialPorts.add(null);
		}	
		
		
		availablePorts();
		
		serial = setup(9600, "Do you want to skip setup for the rfid(y/n)", 1);
		
		if (serial != null) {
			serial.openPort();
			serialPorts.add(serial);
		}else {
			serialPorts.add(null);
		}

		
		System.out.println();
		
		availablePorts();
		
		serial = setup(9600, "Do you want to skip setup for the dispenser(y/n)", 2);
		
		if (serial != null) {
			serial.openPort(); 
			serialPorts.add(serial);
			
		}else {
			serialPorts.add(null);
		}
		
	
		serial = setup(9600, "Do you want to skip setup for the bonPrinter(y/n)" ,3);
		
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
	 * 
	 * @param boudRate and String to display
	 * @return
	 */
	private SerialConnection setup(int boudRate, String sysoutString, int place) {
		if (portNames.isEmpty()) return null;
		
		System.out.println(sysoutString);
		
		if(!userinput()) {
			var a = portComToUse();
			config[place] = a;
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
	 * 
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
	 * 
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
	 * Selects comport to use
	 * 
	 * @return String
	 */
	private String portComToUse() {
		System.out.println("Welke port wilt u gebruiken(input id)");
		while(true) {
			var userInput =  scanner.nextLine();
			try {
				var a = Integer.parseInt(userInput);
				if(portNames.size() < a ) continue;
				var b = portNames.get(a);
				return b;
				
			} catch (NumberFormatException  e) {
				System.out.println("Dat id bestaat niet");
			}
		}	
	}
	
	/**
	 * Gets all portnames
	 * 
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
	
	/**
	 * Put config[0] with baudrate 115200 as a serial connection and add serial
	 * and do this for config[1] - config[2] with baud rate 9600
	 * 
	 * If config[3] is not null add a serial connection with config[3] and baudrate 9600
	 * execute openPort with that serialConnection
	 * and add the serialConnection to serial.
	 * if config[3] is null add null to serial
	 * 
	 * @return ArrayList<SerialConnection> serial
	 */
	public ArrayList<SerialConnection> getPorts() {
		
		ArrayList<SerialConnection> serial = new ArrayList<SerialConnection>();
		var a = new SerialConnection(config[0], 115200);
		a.openPort();
		
		serial.add(a);
		
		var b = new SerialConnection(config[1], 9600);
		b.openPort();
		serial.add(b);
		
		
		var c = new SerialConnection(config[2], 9600);
		c.openPort();
		serial.add(c);
		
		
		if(!config[3].contains("null")) {
			var d = new SerialConnection(config[3], 9600);
			d.openPort();
			serial.add(d);
		}else {
			serial.add(null);
		}
		
		return serial;
	}
}
