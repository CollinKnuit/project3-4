package org.FF.GUI.app;

import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.config.FileUpdate;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class TestApp2 {

	private static Scanner scanner = new Scanner(System.in);
	private static ArrayList<String> portnames;

	public static void main(String[] args) throws IOException, InterruptedException {
		portnames = getAllPortNames();

		for (int i = 0; portnames.size() > i; i++) {
			System.out.println(i + " " + portnames.get(i));
		}

		String a = portComToUse();

		var c = new SerialConnection(a, 9600);
		System.out.println(c);
		c.openPort();
		
		Thread.sleep(3000);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String pinAmount = Integer.toString(200);
		String rfidNumber = "1234";
		String transactionNumber = Integer.toString(1000);
		String dateSent = formatter.format(date);
		c.sendData(pinAmount + rfidNumber + transactionNumber + dateSent);
		System.out.println(pinAmount + rfidNumber + transactionNumber + dateSent);
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

	private static String portComToUse() {
		System.out.println("Welke port wilt u gebruiken(input id)");
		while (true) {
			var userInput = scanner.nextLine();
			try {
				var a = Integer.parseInt(userInput);
				if (portnames.size() < a)
					continue;
				var b = portnames.get(a);
				return b;

			} catch (NumberFormatException e) {
				System.out.println("Dat id bestaat niet");
			}
		}
	}

}
