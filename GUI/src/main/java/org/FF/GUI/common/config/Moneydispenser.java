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
	 * @param int money
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
	 * @param int money
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
	 * @param int money
	 * @return true if successful 
	 */
	public boolean updateBanknotes_50(int money) {
		var newBedrag = banknotes_50 - Math.abs(money);
		
		if(newBedrag == 0) return false;
		
		banknotes_50 = newBedrag;
		config.getData()[6] = Integer.toString(newBedrag);
		
		return true;
	}
	
/**
 * If all banknotes types are above 5, Show available options 1,4
 * 		And if amount of money is greater than 20, show also option 1
 * 		Or if amount of money is greater than 50, show also option 3
 * 
 * option 1 is true when you have more than 4 banknotes of 10 and more than 3 banknotes of 50
 * option 2 is true when you have more than 1 or equals 1 banknotes of 10 and more than 4 banknotes of 20 and more than 2 banknotes of 50
 * option 3 is true when you have more than 1 or equals 1 banknotes of 10 and more than 1 banknotes of 20 and more than 4 banknotes of 50
 * option 3 is also true when you have more than 4 banknotes of 10 and more than 4 banknotes of 20 and more than 1 banknotes of 50
 * option 4 is true when all other options are true too. 
 * @param money
 * @return
 */
	
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
	
/**
 * Updates the config
 * @throws IOException
 */
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
