package com.techelevator.items;

public class TotalSystemSaleItem {

    private String name;
    private int quantity;
    private double totalPrice;

    public TotalSystemSaleItem(String name, int quantity, double totalPrice) {
        this.name = name;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj instanceof TotalSystemSaleItem){
            return this.name.equals(((TotalSystemSaleItem) obj).getName()) && this.quantity == ((TotalSystemSaleItem) obj).getQuantity()
                    && this.totalPrice == ((TotalSystemSaleItem) obj).getTotalPrice();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + quantity;
        hash = 19 * hash + (int) totalPrice;
        hash = 19 * hash + (name == null ? 0 : name.hashCode());
        return hash;
    }

    //Getters and Setters:

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


}
