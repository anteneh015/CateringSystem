package com.techelevator.view;

import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;
import com.techelevator.items.Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * This is the only class that should have any usage of System.out or System.in
 *
 * Usage of System.in or System.out should not appear ANYWHERE else in your code outside of this class.
 *
 * Work to get input from the user or display output to the user should be done in this class, however, it
 * should include no "work" that is the job of the catering system.
 */
public class Menu {
	
	private static final Scanner in = new Scanner(System.in);
	private Inventory inventory;

	public void showWelcomeMessage() {
		System.out.println("*************************");
		System.out.println("**     Weyland Corp.   **");
		System.out.println("**      Catering       **");
		System.out.println("*************************");
		System.out.println();
	}

	public void showMainMenu() {
		System.out.println("(1) Display Catering Items");
		System.out.println("(2) Order");
		System.out.println("(3) Quit");

		if(in.nextLine().equals("1")){
			for(CateringItem item : inventory.getInventoryList()){
				System.out.println(item);
			}
		}


	}

	public void inventory() throws FileNotFoundException {
		System.out.println("Enter the exact path of the inventory file: ");
		String filePath = in.nextLine();
		System.out.println("Enter the file name: ");
		String fileName = in.nextLine();
		InventoryFileReader fileReader = new InventoryFileReader(fileName,filePath);
		inventory = new Inventory();
		inventory.setInventoryList(fileReader.readFile());

	}

	public void restock(){

		for(CateringItem item : inventory.getInventoryList()){
			item.setProductCount(25);
		}

	}


}
