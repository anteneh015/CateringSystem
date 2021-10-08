package com.techelevator;

import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;

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
		try {
			CateringSystem cateringSystem = new CateringSystem(filePathAsString);
		} catch (FileNotFoundException e) {
			File inventoryFile = new File(filePathAsString);
			menu.displayFileNotFound(inventoryFile);
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
			menu.showMainMenu();
			// if (1) Display Items

			// else if (2) Order Menu
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
