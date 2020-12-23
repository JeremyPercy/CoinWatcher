package controller;

import interfaces.CoinInterface;
import interfaces.ModelInterface;
import model.*;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Index controller is used for initializing data.
 * First time boot, or starting the app will need this class to create the Database, import and/or update data.
 */
public class IndexController {

    private ImportController importCoins;

    public IndexController() {
        importCoins = new ImportController();
    }

    /**
     * First entry point of application
     * Runs boot script
     */
    public void indexAction () {
        try {
            //Create all database if it not exist
            createDB();
            setDefaultPreferences();
        } catch (Exception e) {
            System.out.println("Whoops, something went wrong: " + e.getMessage());
        }
    }

    /**
     * Imports portfolio coins from exchanges
     * @throws Exception
     */
    public void importCoins() throws Exception {

        ArrayList<PortfolioCoin> Coins =  importCoins.importPortfolioCoinsFromBittrex();
        for(ModelInterface Coin : Coins) {
            Coin.createOrUpdate();
        }
        importBTCTickers(Coins);
    }

    public void importBTCTickers(ArrayList<PortfolioCoin> coins) throws Exception {
        for (CoinInterface coin : coins) {
            Ticker ticker = new Ticker();
            String currency = coin.getCurrency();
            JSONObject tickerValues = importCoins.getTickerInBTC(currency);
            if(tickerValues.isEmpty()){
                continue;
            }
            ticker.setMarket("BTC-" + currency);
            ticker.setBid(tickerValues.getDouble("Bid"));
            ticker.setAsk(tickerValues.getDouble("Ask"));
            ticker.setLast(tickerValues.getDouble("Last"));
            ticker.createOrUpdate();
        }
    }

    /**
     * Creates DB when not exists
     */
    private void createDB() {
        for(ModelInterface model : getAllModels()) {
            model.createDbTable();
        }
    }

    private void setDefaultPreferences() {
        PreferencesController preferences = new PreferencesController();
        List<Preferences> preferencesList = preferences.getAllPreferences();
        if(preferencesList.isEmpty()){
            preferences.setDefaultSettings();
        }
    }

    private ArrayList<ModelInterface> getAllModels() {
        ArrayList<ModelInterface> models = new ArrayList<>();
            models.add(new PortfolioCoin());
            models.add(new User());
            models.add(new MarketCoin());
            models.add(new Preferences());
            models.add(new Ticker());
            models.add(new History());
        return models;
    }

}
