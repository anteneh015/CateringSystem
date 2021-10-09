package com.techelevator;

import com.techelevator.items.CateringItem;
import org.junit.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CateringSystemTests {

    private CateringSystem cateringSystem;

    @Before
    public void setup() throws FileNotFoundException, IOException {
        cateringSystem = new CateringSystem("cateringsystem.csv");
    }

    @Test
    public void get_change_input_rounding() throws IOException {
        cateringSystem.setAccountBalance(36.4044444444444444);
        Map<String, Integer> expectedValue = new LinkedHashMap<String, Integer>();
        expectedValue.put("20(s)", 1);
        expectedValue.put("10(s)", 1);
        expectedValue.put("5(s)", 1);
        expectedValue.put("1(s)", 1);
        expectedValue.put("Quarter(s)", 1);
        expectedValue.put("Dime(s)", 1);
        expectedValue.put("Nickel(s)", 1);
        Assert.assertEquals(expectedValue, cateringSystem.getChange());
    }

    @Test
    public void get_change_under_one_dollar() throws IOException {
        cateringSystem.setAccountBalance(0.404444444);
        Map<String, Integer> expectedValue = new LinkedHashMap<String, Integer>();
        expectedValue.put("Quarter(s)", 1);
        expectedValue.put("Dime(s)", 1);
        expectedValue.put("Nickel(s)", 1);
        Assert.assertEquals(expectedValue, cateringSystem.getChange());
    }

    @Test
    public void get_change_zero() throws IOException {
        cateringSystem.setAccountBalance(0);
        Map<String, Integer> expectedValue = new LinkedHashMap<String, Integer>();
        Assert.assertEquals(expectedValue, cateringSystem.getChange());
    }
    @Test
    public void add_item_to_cart_insufficient_funds() throws IOException {
        Map<CateringItem, Integer> expectedShoppingCart = new HashMap<CateringItem, Integer>();
        cateringSystem.setAccountBalance(0);
        cateringSystem.setInventoryList(new ArrayList<CateringItem>());
        cateringSystem.getInventoryList().add(new CateringItem("wine", 4.50, "B4", "Beverage"));
        Assert.assertEquals("Insufficient funds.", cateringSystem.addItemToCart("B4", 1));
        Assert.assertEquals(0, cateringSystem.getAccountBalance(), .009);
        Assert.assertEquals("25", cateringSystem.getInventoryList().get(0).getProductCount());
        Assert.assertEquals(expectedShoppingCart, cateringSystem.getShoppingCart());
    }
    @Test
    public void add_item_to_cart_product_code_not_found() throws IOException {
        Map<CateringItem, Integer> expectedShoppingCart = new HashMap<CateringItem, Integer>();
        cateringSystem.setAccountBalance(5);
        cateringSystem.setInventoryList(new ArrayList<CateringItem>());
        cateringSystem.getInventoryList().add(new CateringItem("wine", 4.50, "B4", "Beverage"));
        Assert.assertEquals("Product code not found", cateringSystem.addItemToCart("G8", 1));
        Assert.assertEquals(5, cateringSystem.getAccountBalance(), .009);
        Assert.assertEquals("25", cateringSystem.getInventoryList().get(0).getProductCount());
        Assert.assertEquals(expectedShoppingCart, cateringSystem.getShoppingCart());
    }
    @Test
    public void add_item_to_cart_item_sold_out() throws IOException {
        Map<CateringItem, Integer> expectedShoppingCart = new HashMap<CateringItem, Integer>();
        cateringSystem.setAccountBalance(5);
        cateringSystem.setInventoryList(new ArrayList<CateringItem>());
        cateringSystem.getInventoryList().add(new CateringItem("wine", 4.50, "B4", "Beverage"));
        cateringSystem.getInventoryList().get(0).setProductCount(0);
        Assert.assertEquals("Item is SOLD OUT", cateringSystem.addItemToCart("B4", 1));
        Assert.assertEquals(5, cateringSystem.getAccountBalance(), .009);
        Assert.assertEquals("SOLD OUT", cateringSystem.getInventoryList().get(0).getProductCount());
        Assert.assertEquals(expectedShoppingCart, cateringSystem.getShoppingCart());

    }
    @Test
    public void add_item_to_cart_not_enough() throws IOException {
        CateringItem testCateringItem = new CateringItem("wine", 4.50, "B4", "Beverage");
        Map<CateringItem, Integer> expectedShoppingCart = new HashMap<CateringItem, Integer>();
        cateringSystem.setAccountBalance(10);
        cateringSystem.setInventoryList(new ArrayList<CateringItem>());
        cateringSystem.getInventoryList().add(testCateringItem);
        cateringSystem.getInventoryList().get(0).setProductCount(1);
        Assert.assertEquals("You requested 2, we only have 1 left.", cateringSystem.addItemToCart("B4", 2));
        Assert.assertEquals(10, cateringSystem.getAccountBalance(), .009);
        Assert.assertEquals("1", cateringSystem.getInventoryList().get(0).getProductCount());
        Assert.assertEquals(expectedShoppingCart, cateringSystem.getShoppingCart());
    }
    @Test
    public void add_item_to_cart_successful() throws IOException {
        CateringItem testCateringItem = new CateringItem("wine", 4.50, "B4", "Beverage");
        Map<CateringItem, Integer> expectedShoppingCart = new HashMap<CateringItem, Integer>();
        expectedShoppingCart.put(testCateringItem, 1);
        cateringSystem.setAccountBalance(5);
        cateringSystem.setInventoryList(new ArrayList<CateringItem>());
        cateringSystem.getInventoryList().add(testCateringItem);
        Assert.assertEquals("Item successfully added to cart", cateringSystem.addItemToCart("B4", 1));
        Assert.assertEquals(.5, cateringSystem.getAccountBalance(), .009);
        Assert.assertEquals("24", cateringSystem.getInventoryList().get(0).getProductCount());
        Assert.assertEquals(expectedShoppingCart, cateringSystem.getShoppingCart());
    }










}
