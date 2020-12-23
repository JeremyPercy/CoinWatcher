package controller;

import model.PortfolioCoin;
import util.DatabaseConnection;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class nImportControllerTest {

    public nImportControllerTest() {
        DatabaseConnection.initializeDBConnection();
    }

    @org.junit.jupiter.api.Test
    void importPortfolioCoinsFromBittrex() {
        ImportController importer = new ImportController();
        ArrayList<PortfolioCoin> coins = importer.importPortfolioCoinsFromBittrex();
        assertNotNull(coins);
        for (PortfolioCoin coin : coins) {
            String currency = coin.getCurrency();
            int length = currency.length();
            System.out.println(currency);
            assertFalse(length >= 5, "Error, currency name length is too high");
            assertFalse(length <= 2, "Error, currency name length is too short");
        }
    }

    @org.junit.jupiter.api.Test
    void importMarketsFromBittrex() {
    }
}