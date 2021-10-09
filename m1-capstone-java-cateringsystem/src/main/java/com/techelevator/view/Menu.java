package com.techelevator.view;


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
public void  invalidMenuInput(){
		System.out.println("Invalid input, please try again.");
	System.out.println();
	}
	// file not found method
	public void displayFileNotFound(File file) {
		System.out.println("Could not find file at file path: " + file.getAbsolutePath());
		System.out.println();
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
		System.out.println();
		return filePath;
	}

	public double getMoneyToAdd() throws NumberFormatException{
		System.out.println("How much money would you like to add? (in whole dollars, limit: $4500)");

			double userBalance = Double.parseDouble(in.nextLine());

		System.out.println();
		return userBalance;
	}

	public void displayCateringItems(List<CateringItem> inventory) {
		System.out.printf("%-4s %-30s %-8s %-4s %n", "Code", "Name", "Price", "QTY");
		System.out.println("-----------------------------------------------------");
		for (CateringItem item : inventory) {
			System.out.printf("%-4s %-30s %-8s %-4s %n",
					item.getProductCode(), item.getName(),
					currency.format(item.getPrice()), item.getProductCount());
		}
		System.out.println();
	}

	public void displayInvalidInputError() {
		System.out.println("Invalid input, please try again.");
		System.out.println();
	}

	public void displayChange(Map<String, Integer> changeMap, double accountBalance){
		System.out.println("Your change is: " + currency.format(accountBalance));
		for(Map.Entry<String, Integer> nextEntry : changeMap.entrySet() ){
			String key = nextEntry.getKey();
			Integer value = nextEntry.getValue();
			System.out.println(value + " " + key);
		}
		System.out.println();
	}
	public void displayTransactionReport(Map<CateringItem, Integer> shoppingCart){
		double totalPrice = 0;
		System.out.printf("%-4s %-15s %-25s %10s %11s %n", "QTY.", "Type", "Name", "Price", "Total");
		for (Map.Entry<CateringItem, Integer> nextEntry : shoppingCart.entrySet()){
			CateringItem key = nextEntry.getKey();
			Integer value = nextEntry.getValue();
			System.out.printf("%-4s %-15s %-25s %10s %11s %n",
					value, key.getProductType(), key.getName(),
					currency.format(key.getPrice()), currency.format(value * key.getPrice()));
			totalPrice += value * key.getPrice();
		}
		System.out.println();
		System.out.println("Order Total: " + currency.format(totalPrice));
		System.out.println();
	}

	public String askForProductCode() {
		System.out.println("Input the Product Code of the desired item: ");
		return in.nextLine();
	}

	public int askForQuantity() throws NumberFormatException{
		System.out.println("How many would you like?");
		return Integer.parseInt(in.nextLine());
	}

	public void shoppingCartMessage(String message) {
		System.out.println(message);
		System.out.println();
	}
	public void displayBalanceLimitError(){
		System.out.println("Balance limit exceeded, please try again.");
	}

	public void logFileWritingError() {
		System.out.println("Error: could not write to Log");
	}
}
