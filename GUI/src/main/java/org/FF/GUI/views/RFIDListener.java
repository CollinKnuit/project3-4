package org.FF.GUI.views;

import java.sql.SQLException;

import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.DatabaseQueryClass;

import java.util.concurrent.atomic.AtomicBoolean;

public class RFIDListener extends Thread{
	
	private Painter painter;
	private DatabaseQueryClass query = new DatabaseQueryClass();
	private SerialConnection serialConnectionRFID;
	private AtomicBoolean suspend = new AtomicBoolean(true);

	/**
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */

	public RFIDListener(Painter painter, SerialConnection serialConnection) {
		this.painter = painter;
		this.serialConnectionRFID = serialConnection;
	}
	
	/**
	 * suspend the thread
	 */
	public void suspendThread() {
		synchronized (this) {
			serialConnectionRFID.removePortListener();
			suspend.compareAndSet(false, true);
		}
	}
	
	/**
	 * activate the thread
	 */
	public void activateThread() {
		synchronized (this) {
			serialConnectionRFID.addPortListener();
			suspend.compareAndSet(true, false);		
			this.notify();
		}
		
	}
	
	/**
	 * if suspend is false wait otherwise continue with the method
	 * store the input from the RFID in rfids and set acountID to -1
	 * go through everything in rfids. At the beginning of the loop set acountID to -1
	 * try to update acountID with the method checkRFID(r) 
	 * if acountID is still -1 then go to the next item in the rfids otherwise update acountID in acount with the parameter acountID.
	 * after that change the displayed screen to FL1_1 (wich is the login screen).
	 */
	@Override
	public void run() {
		
		while(true) {	
			
			synchronized (this) {
				while(suspend.get()) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			var rfids = serialConnectionRFID.getWaitingQueue();
			var acountID = -1;
			for(var r: rfids) {
				
				 acountID = -1;
				 
				try {
					acountID = query.checkRfid(r);
				} catch (SQLException e1) {
					continue;
				}

				if(acountID == -1){
					continue;
				}

				painter.setAcountID(acountID);
				painter.switchPane(ImgBackgrounds.FL1_1);
					
			}
		}
	}



}