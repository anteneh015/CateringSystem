package com.techelevator;

import org.junit.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CateringSystemTests {

    private CateringSystem cateringSystem;

    @Before
    public void setup() throws FileNotFoundException, IOException {
        cateringSystem = new CateringSystem("cateringsystem.csv");
    }

    @Test
    public void get_change_input_rounding(){
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
    public void get_change_under_one_dollar(){
        cateringSystem.setAccountBalance(0.404444444);
        Map<String, Integer> expectedValue = new LinkedHashMap<String, Integer>();
        expectedValue.put("Quarter(s)", 1);
        expectedValue.put("Dime(s)", 1);
        expectedValue.put("Nickel(s)", 1);
        Assert.assertEquals(expectedValue, cateringSystem.getChange());
    }

    @Test
    public void get_change_zero(){
        cateringSystem.setAccountBalance(0);
        Map<String, Integer> expectedValue = new LinkedHashMap<String, Integer>();
        Assert.assertEquals(expectedValue, cateringSystem.getChange());
    }








}
