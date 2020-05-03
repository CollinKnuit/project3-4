package org.FF.GUI.common;

import java.io.IOException;

public class Moneydispenser {

	private int banknotes_10 = 50; 
	private int banknotes_20 = 50;
	private int banknotes_50 = 50;
	private FIleupdate config;
	
	public Moneydispenser(FIleupdate file) {
		// TODO Auto-generated constructor stub
		this.config = file;
	}
	
	public void updateBanknotes_10() {
		config.getData()[4] = Integer.toString(banknotes_10);
	}
	
	public void updateBanknotes_20() {
		config.getData()[5] = Integer.toString(banknotes_20);
	}
	
	public void updateBanknotes_50() {
		config.getData()[6] = Integer.toString(banknotes_50);
	}
	
	public void updateConfig() throws IOException {
		config.updateFile();
	}
	
	public void resetBanknotes() {
		
	}

	public int getBankotes_10() {
		return banknotes_10;
	}

	public int getBanknotes_20() {
		return banknotes_20;
	}

	public int getBanknotes_50() {
		return banknotes_50;
	}
	
}
