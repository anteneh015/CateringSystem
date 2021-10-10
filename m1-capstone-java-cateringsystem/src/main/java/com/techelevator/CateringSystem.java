package com.techelevator;

/*
    This class should encapsulate all the functionality of the Catering system, meaning it should
    contain all the "work"
 */


import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.filereader.LogFileWriter;
import com.techelevator.items.CateringItem;
import com.techelevator.items.TotalSystemSaleItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class CateringSystem {

    public static final int MAXIMUM_ACCOUNT_BALANCE = 4500;
    private double accountBalance = 0;
    private List<CateringItem> inventoryList;
    private Map<CateringItem, Integer> shoppingCart = new HashMap<CateringItem, Integer>();
    private LogFileWriter logFileWriter = new LogFileWriter();
    private List<TotalSystemSaleItem> totalSystemSales = new ArrayList<TotalSystemSaleItem>();

    public CateringSystem (String file) throws FileNotFoundException, IOException {
        InventoryFileReader fileReader = new InventoryFileReader(file);
        this.inventoryList = fileReader.readFile();
        File systemSalesFile = new File("TotalSales.rpt");
        systemSalesFile.createNewFile();
        File previousSystemSalesFile = new File("PreviousTotalSales.rpt");
        previousSystemSalesFile.createNewFile();
        totalSystemSales = fileReader.readTotalSalesFile(previousSystemSalesFile);
    }

    public boolean addAccountBalance(double moneyToAdd) throws IOException {
        if(accountBalance + moneyToAdd > MAXIMUM_ACCOUNT_BALANCE || moneyToAdd % 1 != 0 || moneyToAdd < 0){
            return false;
        }
        this.accountBalance += moneyToAdd;
        logFileWriter.printAddAccountBalance(moneyToAdd, accountBalance);
        return true;
    }

    public void subtractAccountBalance(double moneyToSubtract){
        if(accountBalance >= moneyToSubtract && moneyToSubtract >= 0){
            this.accountBalance -= moneyToSubtract;
        }
    }

    public String  addItemToCart(String desiredItem, int desiredQuantity) throws IOException {
        boolean codeIsFound = false;
        for(CateringItem item : this.inventoryList){
            if(desiredItem.equals(item.getProductCode())){
                codeIsFound = true;

                if(item.getProductCount().equals("SOLD OUT")){
                    return "Item is SOLD OUT";
                }
                if(desiredQuantity > Integer.parseInt(item.getProductCount())){
                    return "You requested " + desiredQuantity + ", we only have " + item.getProductCount() + " left.";
                }
                if(item.getPrice()*desiredQuantity > getAccountBalance()) {
                    return "Insufficient funds.";
                }

                this.shoppingCart.put(item, desiredQuantity);
                subtractAccountBalance(item.getPrice() * desiredQuantity);
                item.purchaseItem(desiredQuantity);
                logFileWriter.printItemAddedToCart(item, desiredQuantity, accountBalance);

            }
        }if (codeIsFound == false){
            return "Product code not found";

        }
        return "Item successfully added to cart";
    }

    public void addToTotalSystemSales() {
        for (Map.Entry<CateringItem, Integer> entry : shoppingCart.entrySet()){
            int shoppingCartItemQuantity = entry.getValue();
            double shoppingCartItemTotalPrice = entry.getKey().getPrice() * shoppingCartItemQuantity;
            String shoppingCartItemName = entry.getKey().getName();
            if (totalSystemSales.size() == 0) {
                totalSystemSales.add(new TotalSystemSaleItem(shoppingCartItemName, shoppingCartItemQuantity, shoppingCartItemTotalPrice));
            } else {
                boolean isFound = false;
                for (TotalSystemSaleItem item : totalSystemSales) {
                    if (shoppingCartItemName.equals(item.getName())) {
                        item.setQuantity(item.getQuantity() + shoppingCartItemQuantity);
                        item.setTotalPrice(item.getTotalPrice() + shoppingCartItemTotalPrice);
                        isFound = true;
                    }
                }
                if (!isFound) {
                    totalSystemSales.add(new TotalSystemSaleItem(shoppingCartItemName, shoppingCartItemQuantity, shoppingCartItemTotalPrice));
                }
            }
        }
    }

    public void updateTotalSystemSalesLog() throws IOException {
        logFileWriter.printTotalSystemSales(totalSystemSales);
    }

    public Map<String, Integer> getChange() throws IOException {
        // this lets us round to 2 decimal places
        BigDecimal remainingBalance = BigDecimal.valueOf(this.accountBalance).setScale(2, RoundingMode.HALF_UP);
        Map<String, Integer> changeMap = new LinkedHashMap<>();
        String bill = "";
        BigDecimal zero = new BigDecimal(0);
        BigDecimal twentyFiveCents = new BigDecimal(.25).setScale(2, RoundingMode.DOWN);
        BigDecimal tenCents = new BigDecimal(.10).setScale(2, RoundingMode.DOWN);
        BigDecimal fiveCents = new BigDecimal(.05).setScale(2, RoundingMode.DOWN);

        while (remainingBalance.compareTo(zero) > 0) {
            // remainingBalance -         20                 > = 0
            if (remainingBalance.subtract(new BigDecimal(20)).compareTo(zero) >= 0) {
                bill = "20(s)";
                remainingBalance = remainingBalance.subtract(new BigDecimal(20));
            } else if (remainingBalance.subtract(new BigDecimal(10)).compareTo(zero) >= 0) {
                bill = "10(s)";
                remainingBalance = remainingBalance.subtract(new BigDecimal(10));
            } else if (remainingBalance.subtract(new BigDecimal(5)).compareTo(zero) >= 0) {
                bill = "5(s)";
                remainingBalance = remainingBalance.subtract(new BigDecimal(5));
            } else if (remainingBalance.subtract(new BigDecimal(1)).compareTo(zero) >= 0) {
                bill = "1(s)";
                remainingBalance = remainingBalance.subtract(new BigDecimal(1));
            } else if (remainingBalance.subtract(twentyFiveCents).compareTo(zero) >= 0){
                bill = "Quarter(s)";
                remainingBalance = remainingBalance.subtract(twentyFiveCents);
            } else if (remainingBalance.subtract(tenCents).compareTo(zero) >= 0) {
                bill = "Dime(s)";
                remainingBalance = remainingBalance.subtract(tenCents);
            } else if (remainingBalance.subtract(fiveCents).compareTo(zero) >= 0){
                bill = "Nickel(s)";
                remainingBalance = remainingBalance.subtract(fiveCents);
            }
            if(changeMap.containsKey(bill)){
                changeMap.put(bill, changeMap.get(bill) + 1 );
            }else {
                changeMap.put(bill, 1);
            }
        }
        logFileWriter.printGiveChange(accountBalance);
        subtractAccountBalance(accountBalance);
        return changeMap;
    }

    public void createNewShoppingCart() {
        this.shoppingCart = new HashMap<CateringItem, Integer>();
    }

    // Getters and Setters:

    public double getAccountBalance() {
        return accountBalance;
    }

    public List<CateringItem> getInventoryList() {
        return inventoryList;
    }

    public Map<CateringItem, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setInventoryList(List<CateringItem> inventoryList) {
        this.inventoryList = inventoryList;
    }
}
