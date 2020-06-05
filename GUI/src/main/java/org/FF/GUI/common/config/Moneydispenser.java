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
	
	public boolean[] availableOptions(int money) {
		boolean[] availableOptions = {false,false,false,false};
		
		if(banknotes_10 >= 5 && banknotes_20 >= 5 && banknotes_50 >= 5) {
			availableOptions[0] = true;
			availableOptions[3] = true;
			if(money >= 20) {
				availableOptions[1] = true;
			}
			if(money >= 50) {
				availableOptions[2] = true;
			}
			
			
			
			return availableOptions;
		}
		
		if(banknotes_10 >= 5 && banknotes_50 >= 4) {
			availableOptions[0] = true;
		}
		
		if(banknotes_10 >= 1 && banknotes_20 >= 5 && banknotes_50 >= 3 && money >= 20) {
			availableOptions[1] = true;
		}
		
		if(banknotes_10 >= 4 && banknotes_50 >= 5 && money >= 50) {
			availableOptions[2] = true;
		}
		
		if(banknotes_10 >= 1 && banknotes_20 >= 2 && banknotes_50 >= 5 && money >= 50) {
			availableOptions[2] = true;
		}
		
		if(banknotes_10 >= 5 && banknotes_20 >= 5 && banknotes_50 >= 2) {
			availableOptions[0] = true;
			if(money >= 20) {
				availableOptions[1] = true;
			}
		}
		
		if(availableOptions[0] == true || availableOptions[1] == true || availableOptions[2] == true) {
			availableOptions[3] = true;
		}
		
		return availableOptions;
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
