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

    public List<CateringItem> getInventoryList() {
        return inventoryList;
    }

    public Map<CateringItem, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void setInventoryList(List<CateringItem> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public void addItemToCart(String desiredItem, int desiredQuantity){
        for(CateringItem item : this.inventoryList){
            if(desiredItem.equals(item.getProductCode())){
                this.shoppingCart.put(item, desiredQuantity);
                this.totalOrderAmount += (item.getPrice() * desiredQuantity);
                item.purchaseItem(desiredQuantity);
            }
        }
    }

}
