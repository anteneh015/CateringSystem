package com.techelevator;

import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/*
 * This class should control the workflow of the application, but not do any other work
 * 
 * The menu class should communicate with the user, but do no other work
 * 
 * This class should control the logical workflow of the application, but it should do no other
 * work.  It should communicate with the user (System.in and System.out) using the Menu class and ask
 * the CateringSystem class to do any work and pass the results between those 2 classes.
 */
public class CateringSystemCLI {

	/*
	 * The menu class is instantiated in the main() method at the bottom of this file.  
	 * It is the only class instantiated in the starter code.  
	 * You will need to instantiate all other classes using the new keyword before you can use them.
	 * 
	 * Remember every class and data structure is a data types and can be passed as arguments to methods or constructors.
	 */
	private Menu menu;

	public CateringSystemCLI(Menu menu) {
		this.menu = menu;
	}

	/*
	 * Your application starts here
	 */
	public void run() {

		// welcome
		menu.showWelcomeMessage();

		// Get File From User
		String filePathAsString = menu.getFileNameFromUser();

		// Creating Inventory (read file set equal to list)
		CateringSystem cateringSystem = null;
		try {
			cateringSystem = new CateringSystem(filePathAsString);
		} catch (FileNotFoundException e) {
			File inventoryFile = new File(filePathAsString);
			menu.displayFileNotFound(inventoryFile);
			// TODO Make sure this kills the program
			return;
		}

		while (true) {
			/*
			Display the Starting Menu and get the users choice.
			Remember all uses of System.out and System.in should be in the menu

			IF the User Choice is Display Catering Items,
				THEN display vending machine items
			ELSE IF the User's Choice is Purchase,
				THEN go to the purchase menu
			*/

			// Show main menu
			String userMainMenuSelection = menu.showMainMenu();
			// if (1) Display Items
			if(userMainMenuSelection.equals("1")){
				menu.displayCateringItems(cateringSystem.getInventoryList());
				// else if (2) Order Menu
			}else if(userMainMenuSelection.equals("2")){
				while(true) {
					String userOrderMenuSelection = menu.orderMenu(cateringSystem.getAccountBalance());
					if (userOrderMenuSelection.equals("1")) {
						double requestedAddedMoney = menu.getMoneyToAdd();
						boolean moneyIsAdded = cateringSystem.addAccountBalance(requestedAddedMoney);
						if (!moneyIsAdded) {
							menu.displayAddMoneyError();
						}
					} else if (userOrderMenuSelection.equals("2")) {
						String desiredItem = menu.askForProductCode();
						int desiredQuantity = menu.askForQuantity();
						String cartMessage = cateringSystem.addItemToCart(desiredItem, desiredQuantity);
						menu.shoppingCartMessage(cartMessage);
					}else if (userOrderMenuSelection.equals("3")){
						Map<String, Integer> changeMap = cateringSystem.getChange();
						menu.displayChange(changeMap);
						menu.displayTransactionReport(cateringSystem.getShoppingCart());

					}
				}
			}

			//Quit selection
			else if (userMainMenuSelection.equals("3")) {


			}

				// Display Order Menu
					// Do processing of add money
					// purchase
					// complete transaction
			// else if (3) Quit
				// break it all. Screw it!!


		}
	}

	/*
	 * This starts the application, but you shouldn't need to change it.  
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu();
		CateringSystemCLI cli = new CateringSystemCLI(menu);
		cli.run();
	}
}
