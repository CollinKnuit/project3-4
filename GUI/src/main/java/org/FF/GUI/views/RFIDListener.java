package org.FF.GUI.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.FF.GUI.common.SerialConnection.SerialConnection;
import org.FF.GUI.common.database.Acount;
import org.FF.GUI.common.database.DatabaseQueryClass;
import org.FF.GUI.views.KeypadListener;
import org.FF.GUI.views.Painter;

import java.util.ArrayList;

public class RFIDListener implements ActionListener{
	
	private Painter painter;
	private DatabaseQueryClass query = new DatabaseQueryClass();
	private SerialConnection serialConnectionRFID;

	/**
	 * @param painter {@code Painter}
	 * @param serialConnection {@code serialConnection}
	 */

	public RFID(Painter painter, ArrayList<SerialConnection> serialConnection) {
		this.painter = painter;
		this.serialConnectionRFID = serialConnection.get(2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		var rfids = serialConnectionRFID.getWaitingQueue();
	
		for(var r: rfids) {
			
			var a = query.checkRfid(r);

			if(a == -1){
				continue;
			}

			painter.setAcountID(a);

			painter.switchPane(ImgBackgrounds.FL1_1);
				
		}
	}
}