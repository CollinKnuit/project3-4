package org.FF.GUI.common.database;

import java.math.BigDecimal;

public class Acount {
 
	private int AcountID = 0;
	private BigDecimal Balance  = new BigDecimal(100); ;
	private String RfidNumber = "1111112111111";
	private int Password_Atempt_Wrong = 0;
	
	
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

	public int getPassword_Atempt_Wrong() {
		return Password_Atempt_Wrong;
	}

	public void setPassword_Atempt_Wrong(int password_Atempt_Wrong) {
		Password_Atempt_Wrong = password_Atempt_Wrong;
	}
	
	
	
}
