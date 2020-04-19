package org.FF.GUI.common.SerialConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fazecast.jSerialComm.SerialPort;


public class SerialConnection {
	private SerialPort serialPort;
	private ArrayBlockingQueue<String> waitingQueue = new ArrayBlockingQueue<String>(20);
	private Thread portListener;
	private AtomicBoolean exit = new AtomicBoolean(false);

	public SerialConnection(String serialPort, int boud) {
		this.setSerialPort(serialPort, boud);
	}

	public SerialConnection() {
	}


	/**
 	* Opens the port
 	* @return
 	*/
	public boolean openPort() {
		if (serialPort == null) {
			throw new NullPointerException();
		}
		return serialPort.openPort();
	}

	/**
	 * 
	 */
	public void closePort() {
		if (serialPort != null) {
			removePortListener();
			serialPort.closePort();
		}
	}

	/**
	 * adds a portlistner
	 * @throws NullPointerException
	 */
	public void addPortListener() throws NullPointerException {
		if( portListener == null) {
			serialPortListener();
			portListener.start();
		}
		return;
	}

	public void removePortListener() {
		if(exit.get() || portListener == null) return;
		try {
			exit.compareAndSet(false, true);
			portListener.join();
			portListener = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sends data trough the comPort
	 * @param buffer
	 * @throws NullPointerException
	 */
	public void sendData(String buffer) throws NullPointerException {
		serialPort.writeBytes(buffer.getBytes(), buffer.length());
	}
	
	public synchronized String readPort() {
		var bytesAvailable = serialPort.bytesAvailable();

		if (bytesAvailable <= 0) {
			return null;
		}
		
		var newData = new byte[serialPort.bytesAvailable()];
		if (newData.length == serialPort.readBytes(newData, newData.length)) {
		return new String(newData);

		}
		return null;
	}

	/**
	 * sets up a serialPortListener and puts it in the queue
	 */
	private void serialPortListener() {
		if(portListener != null) return;
		exit.compareAndSet(true, false);
		portListener = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!exit.get()) {
					var bytesAvailable = serialPort.bytesAvailable();

					if (bytesAvailable <= 0) {
						continue;
					}
					synchronized (waitingQueue) {
						var newData = new byte[serialPort.bytesAvailable()];
						if (newData.length == serialPort.readBytes(newData, newData.length)) {
							waitingQueue.offer((new String(newData)));

						}
					}
					
				}

			}
		});

	}

	/**
	 * Sets the serialPort settings
	 * @param serialPortToUse
	 * @param boudrate
	 */
	public void setSerialPort(String serialPortToUse, int boud) {
		this.serialPort = this.serialPort == null ? SerialPort.getCommPort(serialPortToUse) : this.serialPort;
		serialPort.setComPortParameters(boud, 
				8, 
				SerialPort.ONE_STOP_BIT, 
				SerialPort.NO_PARITY);
		serialPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
	}

	/**
	 * gets all info thats in the queue
	 * @return
	 */
	public List<String> getWaitingQueue() {
		synchronized (waitingQueue) {
			ArrayList<String> c = new ArrayList<String>();;
			waitingQueue.drainTo(c);
			return c;
		}
	}


}