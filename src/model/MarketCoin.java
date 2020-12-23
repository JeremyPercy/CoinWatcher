package model;

import interfaces.CoinInterface;

public class MarketCoin extends Model implements CoinInterface {
    private String MarketCurrency,
            BaseCurrency,
            MarketCurrencyLong,
            BaseCurrencyLong,
            MarketName;
    private double MinTradeSize;
    private Boolean IsActive;
    private String Created;

    private final static String TB_NAME = "MarketCoin";

    public MarketCoin(String marketCurrency, String baseCurrency, String marketCurrencyLong, String baseCurrencyLong, String marketName, double minTradeSize, Boolean isActive, String created) {
        this.MarketCurrency = marketCurrency;
        this.BaseCurrency = baseCurrency;
        this.MarketCurrencyLong = marketCurrencyLong;
        this.BaseCurrencyLong = baseCurrencyLong;
        this.MarketName = marketName;
        this.MinTradeSize = minTradeSize;
        this.IsActive = isActive;
        this.Created = created;
    }

    public MarketCoin() {
    }

    @Override
    public String toString() {
        return "WalletController.MarketCoin currency: " + getMarketCurrencyLong() +
                ", MinTradeSize: " + getMinTradeSize();
    }

    public String getMarketCurrency() {
        return MarketCurrency;
    }

    public void setMarketCurrency(String marketCurrency) {
        MarketCurrency = marketCurrency;
    }

    public String getCurrency() {
        return BaseCurrency;
    }

    public void setCurrency(String baseCurrency) {
        BaseCurrency = baseCurrency;
    }

    public String getMarketCurrencyLong() {
        return MarketCurrencyLong;
    }

    public void setMarketCurrencyLong(String marketCurrencyLong) {
        MarketCurrencyLong = marketCurrencyLong;
    }

    public String getBaseCurrencyLong() {
        return BaseCurrencyLong;
    }

    public void setBaseCurrencyLong(String baseCurrencyLong) {
        BaseCurrencyLong = baseCurrencyLong;
    }

    public String getMarketName() {
        return MarketName;
    }

    public void setMarketName(String marketName) {
        MarketName = marketName;
    }

    public double getMinTradeSize() {
        return MinTradeSize;
    }

    public void setMinTradeSize(double minTradeSize) {
        MinTradeSize = minTradeSize;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    @Override
    public boolean createDbTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + TB_NAME + " (" +
                "'marketcurrency' string, " +
                "'basecurrency' string, " +
                "'marketcurrencylong' string, " +
                "'basecurrencylong' string, " +
                "'marketname' string, " +
                "'mintradesize' double, " +
                "'isactive' boolean, " +
                "'created' date" +
                ")";
        return this.query("update", query);
    }

    @Override
    public boolean create() {
        return false;
    }

    @Override
    public void createOrUpdate() throws Exception {

    }

    @Override
    public void read() {

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public boolean delete() {
        return false;
    }
}
