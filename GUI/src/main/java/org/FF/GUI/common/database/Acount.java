package org.FF.GUI.common.database;

import java.math.BigDecimal;

public class Acount {

	private int AcountID;
	private BigDecimal Balance;
	private String RfidNumber;
	
	
	public int getAcountID() {
		return AcountID;
	}
	
	public void setAcountID(int acountID) {
		AcountID = acountID;
	}
	
	public BigDecimal getBalance() {
		return Balance;
	}
	
	public void setBalance(BigDecimal balance) {
		Balance = balance;
	}
	
	public String getRfidNumber() {
		return RfidNumber;
	}
	
	public void setRfidNumber(String rfidNumber) {
		RfidNumber = rfidNumber;
	}
	
	
}
