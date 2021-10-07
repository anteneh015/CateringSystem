package com.techelevator.items;

/*
    This represents a single catering item in the system
 */
public class CateringItem {

    private String name;
    private double price;
    private int productCount = 25;
    private String productCode;

    public CateringItem(String name, double price, String productCode){
        this.name = name;
        this.price = price;
        this.productCode = productCode;
    }

    public String getProductCount() {
        if(productCount == 0){
            return "SOLD OUT";
        }
        return String.valueOf(productCount);
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return productCode + " " + name + " " + price + " " + this.getProductCount();
    }
}
