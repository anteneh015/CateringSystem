package com.techelevator.filereader;

import com.techelevator.items.CateringItem;
import com.techelevator.items.TotalSystemSaleItem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*
    This class should contain any and all details of access to the log file
 */
public class LogFileWriter {

    NumberFormat currency = NumberFormat.getCurrencyInstance();
    File logFile = new File("Log.txt");
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");

    public LogFileWriter() throws IOException {
        logFile.createNewFile();
    }

    public void printAddAccountBalance(double moneyToAdd, double accountBalance) throws IOException {
        printLog("ADD MONEY: " + currency.format(moneyToAdd), accountBalance);
    }

    public void printItemAddedToCart(CateringItem item, int quantityAdded, double accountBalance) throws IOException {
        printLog(quantityAdded + " " + item.getName() + " " +
                item.getProductCode() + " " + currency.format(item.getPrice()*quantityAdded), accountBalance);
    }

    public void printGiveChange(double accountBalance) throws IOException {
        printLog("GIVE CHANGE: " + currency.format(accountBalance), 0);
    }

    private void printLog(String transaction, double accountBalance) throws IOException {
        //the true will append the new data
        try (FileWriter fileWriter = new FileWriter(logFile,true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(LocalDateTime.now().format(dateFormat) + " " + transaction + " " + currency.format(accountBalance));
            bufferedWriter.newLine();
        }

    }

    public void printTotalSystemSales(List<TotalSystemSaleItem> totalSystemSales) throws IOException {
        try (FileWriter fileWriter = new FileWriter("TotalSales.rpt",false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            double systemSalesItemsTotal = 0;
            for (TotalSystemSaleItem currentItem : totalSystemSales) {
                bufferedWriter.write(currentItem.getName() + "|" + currentItem.getQuantity() + "|" + currency.format(currentItem.getTotalPrice()));
                bufferedWriter.newLine();
                systemSalesItemsTotal += currentItem.getTotalPrice();
            }
            bufferedWriter.newLine();
            bufferedWriter.write("**TOTAL SALES** " + currency.format(systemSalesItemsTotal));
        }
    }
}
