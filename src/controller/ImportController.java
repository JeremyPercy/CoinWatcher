package controller;

import bittrexApi.Account;
import bittrexApi.Public;
import model.MarketCoin;
import model.PortfolioCoin;
import model.Ticker;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Handles al import functions
 */
public class ImportController {

    private Account BittrexAccount;
    private Public BittrexPublic;
    private ArrayList<PortfolioCoin> portfolioCoins;
    private ArrayList<Ticker> ticker;

    public ImportController() {
        User user = new User();
        user.read();
        if(user.getPublicKey() != null && user.getPrivateKey() != null) {
            this.BittrexAccount = new Account(user.getPrivateKey(), user.getPublicKey());
            this.BittrexPublic = new Public(user.getPrivateKey(), user.getPublicKey());
        }
        this.portfolioCoins = new ArrayList<PortfolioCoin>();
    }

    /**
     * Get portfolioCoins from account and put in database for first run
     *
     * @return
     */
    public ArrayList<PortfolioCoin> importPortfolioCoinsFromBittrex() {
        if(this.BittrexAccount == null) {
            return new ArrayList<PortfolioCoin>();
        }
        JSONArray balances = this.BittrexAccount.getBalances();
        int totalBalances = balances.length();
        for (int i = 0; i < totalBalances; i++) {
            JSONObject jsonBalance = (JSONObject) balances.get(i);
            portfolioCoins.add(new PortfolioCoin(
                    this.BittrexAccount.getName(),
                    jsonBalance.getDouble("Balance"),
                    jsonBalance.getString("Currency"),
                    jsonBalance.getDouble("Available"),
                    jsonBalance.getDouble("Pending"))
            );
        }
        return portfolioCoins;
    }


    /**
     * Gets market from available
     *
     * @return ArrayList WalletController.MarketCoin
     */
    public ArrayList<MarketCoin> importMarketsFromBittrex() {
        if(this.BittrexPublic == null) {
            return new ArrayList<MarketCoin>();
        }
        JSONArray openMarkets = this.BittrexPublic.getMarkets();
        ArrayList<MarketCoin> marketCoins = new ArrayList<MarketCoin>();
        //check if markets is filled before iterating over the markets
        if (openMarkets.isEmpty()) {
            return marketCoins;
        }
        int totalMarkets = openMarkets.length();
        for (int i = 0; i < totalMarkets; i++) {
            JSONObject jsonMarket = (JSONObject) openMarkets.get(i);
            MarketCoin marketCoin = new MarketCoin(
                    jsonMarket.getString("MarketCurrency"),
                    jsonMarket.getString("BaseCurrency"),
                    jsonMarket.getString("MarketCurrencyLong"),
                    jsonMarket.getString("BaseCurrencyLong"),
                    jsonMarket.getString("MarketName"),
                    jsonMarket.getDouble("MinTradeSize"),
                    jsonMarket.getBoolean("IsActive"),
                    jsonMarket.getString("Created")
            );
            marketCoins.add(marketCoin);
        }
        System.out.println(marketCoins);
        return marketCoins;
    }

    /**
     * Gets ticker in BTC - market.
     *
     * A tick size is the minimum price movement of a trading instrument.
     * The price movements of different trading instruments vary with the tick size representing the
     * minimum incremental price movement that can be experienced on an exchange.
     * The tick size increment is expressed in terms of dollars within U.S. markets.
     *
     * Read more: Tick Size https://www.investopedia.com/terms/t/tick-size.asp#ixzz5Ub6hIwsO
     * Follow us: Investopedia on Facebook
     * @param currency
     * @return
     */
    public JSONObject getTickerInBTC(String currency) {
        if (currency.contentEquals("BTC")) {
            return new JSONObject("{}");
        }

        return this.BittrexPublic.getTicker("BTC-" + currency );

    }
}
