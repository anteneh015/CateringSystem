package com.techelevator.items;

import java.util.List;

public class Inventory {

    private List<CateringItem> inventoryList;

    public void setInventoryList(List<CateringItem> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public List<CateringItem> getInventoryList() {
        return inventoryList;
    }

}
