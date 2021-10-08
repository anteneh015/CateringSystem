package com.techelevator.view;

import com.techelevator.CateringSystemCLI;
import com.techelevator.items.CateringItem;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
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

	public String showMainMenu() {
		System.out.println("(1) Display Catering Items");
		System.out.println("(2) Order");
		System.out.println("(3) Quit");

		return in.nextLine();
		// CateringSystem variable in CLI? CLI then tells the System when to make calculations, change inventory, etc
		// ^^ That variable can be public static and then we'll have access anywhere with CateringSystemCLI.CateringSystem
		// for(CateringItem item :
	}

	// file not found method
	public void displayFileNotFound(File file) {
		System.out.println("Could not find file at file path: " + file.getAbsolutePath());
	}

	public String orderMenu(double currentBalance){
		System.out.println("(1) Add money");
		System.out.println("(2) Select products");
		System.out.println("(3) Complete transaction");
		System.out.println("Current account balance: " + currency.format(currentBalance));
		return in.nextLine();
	}

	public String getFileNameFromUser() {
		System.out.println("Enter the exact path of the inventory file including file name: ");
		String filePath = in.nextLine();
//		System.out.println("Enter the file name: ");
//		String fileName = in.nextLine();
		return filePath;
	}

	public double getMoneyToAdd() {
		System.out.println("How much money would you like to add? (in whole dollars, limit: $4500)");
		double userBalance = Double.parseDouble(in.nextLine());
		return userBalance;
	}

	public void displayCateringItems(List<CateringItem> inventory) {
		for (CateringItem item : inventory) {
			System.out.println(item);
		}
	}

	public void displayAddMoneyError() {
		System.out.println("Balance limit exceeded or invalid input, please try again.");
	}

	public void displayChange(Map<String, Integer> changeMap){
		System.out.println("Your change is: ");
		for(Map.Entry<String, Integer> nextEntry : changeMap.entrySet() ){
			String key = nextEntry.getKey();
			Integer value = nextEntry.getValue();
			System.out.println(value + " " + key);
		}
		System.out.println();
	}

	public String askForProductCode() {
		System.out.println("Input the Product Code of the desired item: ");
		return in.nextLine();
	}

	public int askForQuantity() {
		System.out.println("How many would you like?");
		return Integer.parseInt(in.nextLine());
	}

	public void shoppingCartMessage(String message) {
		System.out.println(message);
	}
}
