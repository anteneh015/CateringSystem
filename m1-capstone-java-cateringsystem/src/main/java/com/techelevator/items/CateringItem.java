package com.techelevator.items;

import java.text.NumberFormat;

/*
    This represents a single catering item in the system
 */
public class CateringItem {
NumberFormat currency = NumberFormat.getCurrencyInstance();
    private String name;
    private double price;
    private int productCount = 25;
    private String productCode;
    private String productType;

    public CateringItem(String name, double price, String productCode, String productType){
        this.name = name;
        this.price = price;
        this.productCode = productCode;
        this.productType = productType;
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

    public String getProductType() {
        if(productType.equals("A")){
            return "Appetizer";
        }else if(productType.equals("B")){
            return "Beverage";
        }else if(productType.equals("E")){
            return "Entree";
        }else if (productType.equals("D")){
            return "Dessert";
        }
        return "Invalid Product Type";
    }

    @Override
    public String toString() {
        return productCode + " " + name + " " + currency.format(price) + " (" + this.getProductCount() + " items left)";
    }
}
