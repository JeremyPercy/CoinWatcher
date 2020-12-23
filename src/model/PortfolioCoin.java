package model;

import interfaces.CoinInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PortfolioCoin extends Model implements CoinInterface {

    /**
     * Name of table in database
     */
    private final static String TB_NAME = "PortfolioCoin";

    /**
     * Name of exchange coin is loaded from
     */
    private String exchange;

    /**
     * Available balance of coin
     */
    private Double balance;

    /**
     * Currency name of the coin
     * Example: BTC
     * Unique Identifier
     */
    private String currency;

    /**
     * Available balance for trading
     */
    private Double available;

    /**
     * Pending in order
     */
    private Double pending;

    /**
     * Created at date as string
     */
    private String createdAt;

    /**
     * updated at in date format as string
     */
    private String updatedAt;

    /**
     * Value in USD
     */
    private Double valueUSD;

    /**
     * Value in USD
     */
    private Double valueEUR;

    /**
     * Standard constructor
     */
    public PortfolioCoin() {

    }

    /**
     * Constructor for new Coin
     *
     * @param exchange
     * @param balance
     * @param currency
     * @param available
     * @param pending
     */
    public PortfolioCoin(String exchange, Double balance, String currency, Double available, Double pending) {
        this.exchange = exchange;
        this.balance = balance;
        this.currency = currency;
        this.available = available;
        this.pending = pending;
        this.createdAt = getCreatedAt();
        this.updatedAt = getUpdatedAt();
    }

    /**
     * Constructor for coins from DB
     *
     * @param exchange
     * @param balance
     * @param currency
     * @param available
     * @param pending
     * @param createdAt
     * @param updatedAt
     */
    private PortfolioCoin(String exchange, Double balance, String currency, Double available, Double pending, String createdAt, String updatedAt) {
        this.exchange = exchange;
        this.balance = balance;
        this.currency = currency;
        this.available = available;
        this.pending = pending;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Creates Db table
     *
     * @return boolean
     */
    public boolean createDbTable() {
        return this.query("update", "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                " ('exchange' string, " +
                "'balance' double, " +
                "'currency' string, " +
                "'available' double, " +
                "'pending' double, " +
                "'createdat' string, " +
                "'updatedat' string)");
    }

    /**
     * Tries to create Coin, if already exists it will update the coin instead
     *
     * @throws Exception when currency or exchange is null. These are the two unique identifiers
     */
    public void createOrUpdate() throws Exception {
        this.checkID();

        ArrayList<PortfolioCoin> Coins = this.getAllCoins();
        if (Coins.isEmpty()) {
            System.out.println("Creating coin " + this);
            this.create();
        } else {
            System.out.println("Updating coin " + this);
            this.update();
        }
    }

    /**
     * Get Coins from db
     *
     * @return ArraList with coins
     */
    public ArrayList<PortfolioCoin> getAllCoins() {
        ArrayList<PortfolioCoin> portfolioCoins = new ArrayList<PortfolioCoin>();
        String query = "SELECT * FROM " + TB_NAME + "";
        if (currency != null && exchange != null) {
            String where = " WHERE currency = '" + currency + "' AND exchange = '" + exchange + "'";
            query += where;
        }
        try {
            this.query("result", query);
            ResultSet results = this.result();
            if (results == null) {
                return portfolioCoins;
            }
            while (results.next()) {
                portfolioCoins.add(new PortfolioCoin(
                        results.getString("exchange"),
                        results.getDouble("balance"),
                        results.getString("currency"),
                        results.getDouble("available"),
                        results.getDouble("pending"),
                        results.getString("createdat"),
                        results.getString("updatedat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return portfolioCoins;
        }
        return portfolioCoins;
    }

    /**
     * Inserts new coin into database.
     *
     * @return false if something went wrong
     */
    public boolean create() {
        String sql = "INSERT INTO " + TB_NAME + " ('exchange', 'balance', 'currency', 'available', 'pending', 'createdat') " +
                "VALUES('" +
                this.exchange + "'," +
                this.balance + ",'" +
                this.currency + "'," +
                this.available + "," +
                this.pending + ", '" +
                this.createdAt + "')";
        return this.query("update", sql);
    }

    @Override
    public void read() {

    }

    /**
     * Updates coin in db
     *
     * @return
     * @throws Exception
     */
    public boolean update() throws Exception {
        this.checkID();
        String query =
                "UPDATE  " + TB_NAME + " SET " +
                        "`balance` = '" + this.balance + "'," +
                        "`available` = '" + this.available + "'," +
                        "`pending` = '" + this.pending + "'," +
                        "`updatedat` = '" + this.updatedAt + "'" +
                        " WHERE currency = '" + this.currency + "' AND " +
                        " exchange = '" + this.exchange + "' ";
        return this.query("update", query);
    }

    /**
     * Deletes specified coin from database.
     *
     * @return false if something went wrong
     */
    public boolean delete() throws Exception {
        this.checkID();
        return this.query("update",
                "DELETE FROM " + TB_NAME + " WHERE currency = '" + currency + "' AND exchange = '" + exchange + "'");
    }

    /**
     * Checks ID's from coins. ID consists of combination from Currency and Exchange
     *
     * @throws Exception
     */
    private void checkID() throws Exception {
        if (this.currency == null)
            throw new Exception("Currency cannot be null");

        if (this.exchange == null)
            throw new Exception("Exchange cannot be null");
    }

    public String toString() {
        return getCurrency() + " balance: " + getBalance() + " Last updated: " + getCreatedAt();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCreatedAt() {
        return getCurrentTimeStamp();
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return getCurrentTimeStamp();
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getValueUSD() {
        return valueUSD;
    }

    public void setValueUSD(Double valueUSD) {
        this.valueUSD = valueUSD;
    }

    public Double getValueEUR() {
        return valueEUR;
    }

    public void setValueEUR(Double valueEUR) {
        this.valueEUR = valueEUR;
    }
}
