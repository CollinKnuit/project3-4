package org.FF.GUI.app;

import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.config.FileUpdate;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TestApp2 {
	
	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList<String> portnames;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		portnames = getAllPortNames();
		
		for(int i = 0 ; portnames.size() > i ; i++ ) {
			System.out.println(i + " " + portnames.get(i));
		}
		
		String a = portComToUse();
		
		var c = new SerialConnection(a, 9600);
		System.out.println(c);
		c.openPort();
		System.out.println("test2");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test");
		c.sendData("sake");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test5");
		c.sendData("halo");
		////////////////////////////////////////////////
		
		Thread.sleep(2000);
		
		
		
		

		c.sendData("12345");
		

		
		
		////////////////////////////////////////////////
	}

	
	private static ArrayList<String> getAllPortNames() {
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
	
	private static  String portComToUse() {
		System.out.println("Welke port wilt u gebruiken(input id)");
		while(true) {
			var userInput =  scanner.nextLine();
			try {
				var a = Integer.parseInt(userInput);
				if(portnames.size() < a ) continue;
				var b = portnames.get(a);
				return b;
				
			} catch (NumberFormatException  e) {
				System.out.println("Dat id bestaat niet");
			}
		}	
	}
	
	
}
