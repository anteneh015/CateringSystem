package com.techelevator;

/*
    This class should encapsulate all the functionality of the Catering system, meaning it should
    contain all the "work"
 */


import com.techelevator.filereader.InventoryFileReader;
import com.techelevator.items.CateringItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
        subtractAccountBalance(accountBalance);
        return changeMap;
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

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
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
