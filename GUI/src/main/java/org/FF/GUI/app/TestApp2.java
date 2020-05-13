package org.FF.GUI.app;

import org.FF.GUI.common.SerialConnection.ComPortSetup;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.config.FileUpdate;

import java.io.IOException;
import java.util.ArrayList;

public class TestApp2 {
	
	private static FileUpdate file;
	private static ArrayList<SerialConnection> serialConnection;
	private static SerialConnection serialConnectionBonPrinter;
	
	public static void main(String[] args) throws IOException {
		file = new FileUpdate();
		var a = file.getData();
		
		serialConnection = new ComPortSetup(a).setupComPort();
		serialConnectionBonPrinter = serialConnection.get(/*geef hier aan welke port je wilt hebben zelfde index als bij setup*/);
		
		serialConnectionBonPrinter.sendData("Test");
		serialConnectionBonPrinter.sendData("1");
	}

}
