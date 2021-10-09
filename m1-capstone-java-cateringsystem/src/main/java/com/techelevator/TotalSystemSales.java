package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TotalSystemSales {

    // String[0] is name, String[1] quantity, String[3] price
    private List<String[]> totalSystemSales = new ArrayList<String[]>();

    public TotalSystemSales() throws IOException {
        File systemSalesFile = new File("TotalSales.rpt");
        systemSalesFile.createNewFile();
    }

    public List<String[]> getTotalSystemSales() {
        return totalSystemSales;
    }
}
