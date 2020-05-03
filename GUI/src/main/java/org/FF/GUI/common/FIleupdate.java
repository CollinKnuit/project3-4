package org.FF.GUI.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class FIleupdate {
	private File customDir;
	private File config;
	private String[] data = {"null", "null", "null", "null", "null", "null", "null"};
	
	public FIleupdate() {
		var path = System.getProperty("user.home") + File.separator + "Documents";
		this.customDir = new File(path+=File.separator + "ATMData" );
		this.config = new File(customDir, "config");
		
		if (customDir.exists() || customDir.mkdirs()) {
			
		    try {
				if(!config.createNewFile()) {
					readFromConfig();
				}else {
					updateFile();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		} 
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void readFromConfig() throws IOException {
		Scanner scanner = new Scanner(config);
		
		for(int i = 0; scanner.hasNext(); i++) {
			
		     data[i] = scanner.next(); 
		}
		
		scanner.close();
	}
	
	public void updateFile() throws IOException {
		
		FileWriter fw = new FileWriter(config);

	    for (int i = 0; i < data.length; i++) {
	      fw.write(data[i]+ "\n");
	    }
	    fw.flush();
	    fw.close();
	}


	public String[] getData() {
		return data;
	}
	
	
	
}
