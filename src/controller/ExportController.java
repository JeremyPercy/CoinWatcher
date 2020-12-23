package controller;

import model.PortfolioCoin;
import model.Preferences;
import util.AlertBox;
import util.TextWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to export data from database
 */
class ExportController {

    private PortfolioCoin coin;
    private TextWriter text;

    ExportController() {
        Preferences preferences = new Preferences();
        text = new TextWriter(preferences);
        coin = new PortfolioCoin();
    }

    /**
     * Gets all available coins
     * @return
     */
    boolean exportPortofolioCoins() {
        ArrayList<PortfolioCoin> coins = coin.getAllCoins();
        StringBuilder data = new StringBuilder();
        for (PortfolioCoin coin : coins) {
            data.append(coin.toString()).append("\n");
        }
        return exportToFile(data.toString());
    }

    /**
     * Actually writes the file
     * @param data
     * @return
     */
    private boolean exportToFile(String data) {
        try {
            text.write(data);
            return true;
        } catch (FileNotFoundException e) {
            AlertBox.display("Whoops, cannot write file: " + e.getMessage());
        } catch (IOException e) {
            AlertBox.display("Something went terribly wrong: " + e.getMessage());
        }
        return false;
    }

    String filePath() {
        return text.getAbsolutePath();
    }

}
