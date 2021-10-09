package com.techelevator.filereader;

import com.techelevator.TotalSystemSales;
import com.techelevator.items.CateringItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/*
    This class should contain any and all details of access to the inventory file
 */
public class InventoryFileReader {


    private String filePath;
    private File inventoryFile;

    public InventoryFileReader(String filePath) {
        this.filePath = filePath;
        this.inventoryFile = new File(filePath);
    }

    public List<CateringItem> readFile() throws FileNotFoundException {

        List<CateringItem> inventory = new ArrayList<CateringItem>();

        try(Scanner fileScanner = new Scanner(this.inventoryFile)){

            while(fileScanner.hasNextLine()){

                String line = fileScanner.nextLine();
                String[] splitLine = line.split("\\|");
                double price = Double.parseDouble(splitLine[3]);
                CateringItem cateringItem = new CateringItem(splitLine[2], price, splitLine[1], splitLine[0] );
                inventory.add(cateringItem);
            }

        }
        return inventory;
    }

    public List<String[]> readTotalSalesFile(File file) throws FileNotFoundException, IOException {

        List<String[]> previousTotalSales = new ArrayList<String[]>();

        try(Scanner fileScanner = new Scanner(file)){

            while(fileScanner.hasNextLine()){

                String line = fileScanner.nextLine();
                String[] splitLine = line.split("\\|");
                // 0 item, 1 quantity, 2 price

                previousTotalSales.add(splitLine);
            }

        }
        return previousTotalSales;
    }
}
