package com.techelevator.view;

import com.techelevator.CateringSystemCLI;
import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
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

	NumberFormat currency = NumberFormat.getCurrencyInstance();
	private static final Scanner in = new Scanner(System.in);

	//private CateringSystem cateringSystem = new CateringSystem();

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

		String userInput = in.nextLine();


		// CateringSystem variable in CLI? CLI then tells the System when to make calculations, change inventory, etc
		// ^^ That variable can be public static and then we'll have access anywhere with CateringSystemCLI.CateringSystem
		// for(CateringItem item :
		if(userInput.equals("1")){
			for(CateringItem item : CateringSystemCLI.getCateringSystem().getInventoryList()){
				System.out.println(item);
			}
		}else if(userInput.equals("2")){
			this.orderMenu();
		}


	}
	public void orderMenu(){
		System.out.println("(1) Add money");
		System.out.println("(2) Select products");
		System.out.println("(3) Complete transaction");
		System.out.println("Current account balance: " +
				currency.format(CateringSystemCLI.getCateringSystem().getAccountBalance()));
		String userInput = in.nextLine();
		if(userInput.equals("1")){
			System.out.println("How much money would you like to add? (in whole dollars, limit: $4500)");
			double userBalance = Double.parseDouble(in.nextLine());
			if(CateringSystemCLI.getCateringSystem().addAccountBalance(userBalance) != -1){
				System.out.println("Current account balance: " +
						currency.format(CateringSystemCLI.getCateringSystem().getAccountBalance()));
			}else{
				System.out.println("Balance limit exceeded or invalid input, please try again.");
				orderMenu();
			}
		}else if(userInput.equals("2")){
			System.out.println("Input the Product Code of the desired item: ");
			String desiredItem = in.nextLine();
			System.out.println("How many would you like?");
			int desiredQuantity = Integer.parseInt(in.nextLine());
			String cartMessage = CateringSystemCLI.getCateringSystem().addItemToCart(desiredItem, desiredQuantity);
			System.out.println(cartMessage);
			orderMenu();


		}


	}

	public InventoryFileReader inventory() throws FileNotFoundException {
		System.out.println("Enter the exact path of the inventory file: ");
		String filePath = in.nextLine();
		System.out.println("Enter the file name: ");
		String fileName = in.nextLine();
		InventoryFileReader fileReader = new InventoryFileReader(fileName,filePath);
		return fileReader;

	}

	public void restock(){
		for(CateringItem item : CateringSystemCLI.getCateringSystem().getInventoryList()){
			item.setProductCount(25);
		}
	}


}
