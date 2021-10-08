package com.techelevator;

/*
    This class should encapsulate all the functionality of the Catering system, meaning it should
    contain all the "work"
 */


import com.techelevator.items.CateringItem;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CateringSystem {

    private double totalOrderAmount = 0;
    private double accountBalance = 0;
    private List<CateringItem> inventoryList;
    private Map<CateringItem, Integer> shoppingCart = new HashMap<CateringItem, Integer>();

    public double getAccountBalance() {
        return accountBalance;
    }

    public double getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public double addAccountBalance(double moneyToAdd) {
        if(accountBalance + moneyToAdd > 4500 || moneyToAdd % 1 != 0){
            return -1;
        }
        this.accountBalance += moneyToAdd;
        return this.accountBalance;
    }
   public double subtractAccountBalance(double moneyToSubtract){
       if(accountBalance - moneyToSubtract < 0){
           return -1;
       }

        this.accountBalance -= moneyToSubtract;
       return this.accountBalance;
   }

    public List<CateringItem> getInventoryList() {
        return inventoryList;
    }

    public Map<CateringItem, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setInventoryList(List<CateringItem> inventoryList) {
        this.inventoryList = inventoryList;
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
               if(this.subtractAccountBalance(item.getPrice() * desiredQuantity) == -1) {
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
