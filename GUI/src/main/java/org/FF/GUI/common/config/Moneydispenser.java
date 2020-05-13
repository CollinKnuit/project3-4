package org.FF.GUI.common.config;

import java.io.IOException;

public class Moneydispenser {

	private int banknotes_10; 
	private int banknotes_20;
	private int banknotes_50;
	private FileUpdate config;
	
	public Moneydispenser(FileUpdate file) {
		// TODO Auto-generated constructor stub
		this.config = file;
		this.banknotes_10 = Integer.parseInt(config.getData()[4]); 
		this.banknotes_20 = Integer.parseInt(config.getData()[5]); 
		this.banknotes_50 = Integer.parseInt(config.getData()[6]); 
	}
	
	/**
	 * Updates the now new money count
	 * @param money
	 * @return true if successful 
	 */
	public boolean updateBanknotes_10(int money) {
		var newBedrag = banknotes_10 - Math.abs(money);
		
		if(newBedrag == 0) return false;
		
		banknotes_10 = newBedrag;
		config.getData()[4] = Integer.toString(newBedrag);
		
		return true;
	}
	
	/**
	 * Updates the now new money count
	 * @param money
	 * @return true if successful 
	 */
	public boolean updateBanknotes_20(int money) {
		var newBedrag = banknotes_20 - Math.abs(money);
		
		if(newBedrag == 0) return false;
		
		banknotes_20 = newBedrag;
		config.getData()[5] = Integer.toString(newBedrag);
		
		return true;
	}
	
	/**
	 * Updates the now new money count
	 * @param money
	 * @return true if successful 
	 */
	public boolean updateBanknotes_50(int money) {
		var newBedrag = banknotes_50 - Math.abs(money);
		
		if(newBedrag == 0) return false;
		
		banknotes_50 = newBedrag;
		config.getData()[6] = Integer.toString(newBedrag);
		
		return true;
	}
	
	public void updateConfig() throws IOException {
		config.updateFile();
	}

	public int getBanknotes_10() {
		return banknotes_10;
	}

	public void setBanknotes_10(int banknotes_10) {
		this.banknotes_10 = banknotes_10;
	}

	public int getBanknotes_20() {
		return banknotes_20;
	}

	public int getBanknotes_50() {
		return banknotes_50;
	}
	
	
	
	
	
}
