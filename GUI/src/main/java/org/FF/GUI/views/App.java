package org.FF.GUI.views;
import java.awt.*;  
import javax.swing.*;  
import java.util.Scanner;
public class App {   
        public static void main(String args[])  
        {  
        Painter p = new Painter("FW1_1");
        Scanner input = new Scanner(System.in);
        String i = input.nextLine();
        if (i.equals("S")) {
            p.switchPane("FP1_1");

        	}
        }  
    } 