package org.FF.GUI.views;
import java.awt.*;  
import javax.swing.*;  
import java.util.Scanner;
public class App {   
        public static void main(String args[])  
        {  
        // create a welcome screen.
        Painter p = new Painter("FH1_1");
        Scanner input = new Scanner(System.in);
        String i = input.nextLine();
        if (i.equals("S")) {
            p.switchPane("FS1_1");

        	}
        }  
    } 