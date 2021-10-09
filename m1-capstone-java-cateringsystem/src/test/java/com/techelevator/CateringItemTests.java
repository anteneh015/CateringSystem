package com.techelevator;

import com.techelevator.items.CateringItem;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;

public class CateringItemTests {

    CateringItem testItem;


    @Before
    public void setup(){
        testItem = new CateringItem("Sparkling Water", 1.35, "B1", "B");
    }

    @Test
    public void to_string(){
        String testString = testItem.toString();
        Assert.assertEquals("B1 Sparkling Water $1.35 QTY:25", testString);

    }

    @Test
    public void to_string_sold_out(){
        testItem.setProductCount(0);
        String testString = testItem.toString();
            Assert.assertEquals("B1 Sparkling Water $1.35 QTY:SOLD OUT", testString);
    }

    @Test
    public void purchase_item_25(){
        testItem.purchaseItem(25);
        Assert.assertEquals("SOLD OUT", testItem.getProductCount());
    }
    @Test
    public void purchase_item_26(){
        testItem.purchaseItem(26);
        Assert.assertEquals("25", testItem.getProductCount());
    }
    @Test
    public void purchase_item_negative(){
        testItem.purchaseItem(-5);
        Assert.assertEquals("25", testItem.getProductCount() );
    }
}
