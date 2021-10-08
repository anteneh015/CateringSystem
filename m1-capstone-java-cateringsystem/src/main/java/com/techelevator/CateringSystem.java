package com.techelevator;

/*
    This class should encapsulate all the functionality of the Catering system, meaning it should
    contain all the "work"
 */


import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CateringSystem {

    private double totalOrderAmount = 0;
    private double accountBalance = 0;
    private List<CateringItem> inventoryList;
    private Map<CateringItem, Integer> shoppingCart = new HashMap<CateringItem, Integer>();

    public CateringSystem (String file) throws FileNotFoundException {
        InventoryFileReader fileReader = new InventoryFileReader(file);
        this.inventoryList = fileReader.readFile();
    }

//    public void restock(){
//        for(CateringItem item : CateringSystemCLI.getCateringSystem().getInventoryList()){
//            item.setProductCount(25);
//        }
//    }\
    public Map<String, Integer> getChange() {
        double remainingBalance = accountBalance;
        Map<String, Integer> changeMap = new LinkedHashMap<>();
        String bill = "";


        while (remainingBalance > 0) {
            if (remainingBalance - 20 >= 0) {
                bill = "20(s)";
                remainingBalance -= 20;
            } else if (remainingBalance - 10 >= 0) {
                bill = "10(s)";
                remainingBalance -= 10;
            } else if (remainingBalance - 5 >= 0) {
                bill = "5(s)";
                remainingBalance -= 5;
            } else if (remainingBalance - 1 >= 0) {
                bill = "1(s)";
                remainingBalance -= 1;
            } else if (remainingBalance - .25 >= 0) {
                bill = "Quarter(s)";
                remainingBalance -= .25;
            } else if (remainingBalance - .10 >= 0) {
                bill = "Dime(s)";
                remainingBalance -= .10;
            } else if (remainingBalance - .05 >= 0) {
                bill = "Nickel(s)";
                remainingBalance -= .05;
            }
            if(changeMap.containsKey(bill)){
                changeMap.put(bill, changeMap.get(bill) + 1 );
            }else {
                changeMap.put(bill, 1);
            }

        } return changeMap;
    }
    public double getAccountBalance() {
        return accountBalance;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }


    public boolean addAccountBalance(double moneyToAdd) {
        if(accountBalance + moneyToAdd > 4500 || moneyToAdd % 1 != 0){
            return false;
        }
        this.accountBalance += moneyToAdd;
        return true;
    }

   public boolean subtractAccountBalance(double moneyToSubtract){
       if(accountBalance - moneyToSubtract < 0){
           return false;
       }
        this.accountBalance -= moneyToSubtract;
            return true;
   }

    public List<CateringItem> getInventoryList() {
        return inventoryList;
    }

    public Map<CateringItem, Integer> getShoppingCart() {
        return shoppingCart;
    }


    public String  addItemToCart(String desiredItem, int desiredQuantity){
        boolean codeIsFound = false;
        for(CateringItem item : this.inventoryList){
            if(desiredItem.equals(item.getProductCode())){
                codeIsFound = true;

               if(item.getProductCount().equals("SOLD OUT")){
                   return "Item is Sold Out";
               }
               if(desiredQuantity > Integer.parseInt(item.getProductCount())){
                   return "You requested " + desiredQuantity + ", we only have " + item.getProductCount() + " left.";
               }
               if(!(subtractAccountBalance(item.getPrice() * desiredQuantity))) {
                   return "Insufficient funds.";
               }

                this.shoppingCart.put(item, desiredQuantity);
                this.totalOrderAmount += (item.getPrice() * desiredQuantity);
                item.purchaseItem(desiredQuantity);


            }
        }if (codeIsFound == false){
            return "Product code not found";

        } return "Item successfully added to cart";
    }

}
