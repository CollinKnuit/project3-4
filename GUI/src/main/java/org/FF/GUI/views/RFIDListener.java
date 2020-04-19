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
	 * 
	 */
	public void suspendThread() {
		synchronized (this) {
			serialConnectionRFID.removePortListener();
			suspend.compareAndSet(false, true);
		}
	}
	
	/**
	 * 
	 */
	public void activateThread() {
		synchronized (this) {
			serialConnectionRFID.addPortListener();
			suspend.compareAndSet(true, false);		
			this.notify();
		}
		
	}
	
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
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