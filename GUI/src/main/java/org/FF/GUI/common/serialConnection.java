package org.FF.GUI.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;


import com.fazecast.jSerialComm.SerialPort;


public class serialConnection {
	private SerialPort serialPort;
	private ArrayBlockingQueue<String> waitingQueue = new ArrayBlockingQueue<String>(20);
	private Thread portListener;
	private volatile boolean exit = false;

	public serialConnection(String serialPort, int boud) {
		this.setSerialPort(serialPort, boud);
	}

	public serialConnection() {
	}

	public String[] getAllPortNames() {
		SerialPort[] ports = SerialPort.getCommPorts();
		if (ports.length == 0) {
			System.out.println("No serial ports available!");
			return null;
		}

		String[] result = new String[ports.length];
		for (int i = 0; i < ports.length; i++) {
			result[i] = ports[i].getSystemPortName();
		}

		return result;
	}

	public boolean openPort() {
		if (serialPort == null) {
			throw new NullPointerException();
		}

		return serialPort.openPort();

	}

	public void closePort() {
		if (serialPort != null) {
			removeReaddPortListener();
			serialPort.closePort();
		}
	}

	public void addPortListener() throws NullPointerException {
		if( portListener == null) {
			serialPortListener();
			portListener.start();
		}
		return;
	}
	
	public void removeReaddPortListener() {
		if(exit) return;
		try {
			exit = true;
			portListener.join();
			portListener = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendData(String buffer) throws NullPointerException {
		serialPort.writeBytes(buffer.getBytes(), buffer.length());
	}

	private void serialPortListener() {
		if(portListener != null) return;
		exit = false;
		portListener = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!exit) {
					var bytesAvailable = serialPort.bytesAvailable();

					if (bytesAvailable <= 0) {
						continue;
					}
					var newData = new byte[serialPort.bytesAvailable()];
					if (newData.length == serialPort.readBytes(newData, newData.length)) {
						waitingQueue.offer((new String(newData)));

					}
				}

			}
		});

	}

	public void setSerialPort(String serialPortToUse, int boud) {
		this.serialPort = this.serialPort == null ? SerialPort.getCommPort(serialPortToUse) : this.serialPort;
		serialPort.setComPortParameters(boud, 
				8, 
				SerialPort.ONE_STOP_BIT, 
				SerialPort.NO_PARITY);
		serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
	}

	public List<String> getWaitingQueue() {
		List<String> c = new ArrayList<String>();;
		waitingQueue.drainTo(c);
		return c;
	}

}
