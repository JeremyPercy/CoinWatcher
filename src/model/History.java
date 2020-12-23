package model;

import com.mysql.cj.protocol.Resultset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends Model {
    /**
     * Name of table in database
     */
    private final static String TB_NAME = "History";
    private String updatedAt;
    private String exchange;
    private Double balance;
    private String currency;
    private Double pending;
    private Double available;

    ResultSet result;

    public History() {
    }

    public History (String currency) {
        this.currency = currency;
    }

    public History(String currency, Double balance, String updatedAt) {
        this.currency = currency;
        this.balance = balance;
        this.updatedAt = updatedAt;
    }

    /**
     * Constructor for coins from DB
     *
     * @param exchange
     * @param balance
     * @param currency
     * @param available
     * @param pending
     */
    public History(String exchange, Double balance, String currency, Double available, Double pending) {
        this.exchange = exchange;
        this.balance = balance;
        this.currency = currency;
        this.available = available;
        this.pending = pending;
        this.updatedAt = setDate();
    }

    @Override
    public boolean createDbTable() {
        return this.query("update", "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                " ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'exchange' string, " +
                "'balance' double, " +
                "'currency' string, " +
                "'available' double, " +
                "'pending' double, " +
                "'updatedat' string)");
    }

    @Override
    public boolean create() {
        String sql = "INSERT INTO " + TB_NAME + " ('exchange', 'balance', 'currency', 'available', 'pending', 'updatedat') " +
                "VALUES('" +
                this.exchange + "'," +
                this.balance + ",'" +
                this.currency + "'," +
                this.available + "," +
                this.pending + ", '" +
                this.updatedAt + "')";
        return this.query("update", sql);
    }

    @Override
    public void createOrUpdate() throws Exception {

    }

    @Override
    public void read() {

    }

    @Override
    public boolean update() throws Exception {
        return false;
    }

    @Override
    public boolean delete() throws Exception {
        return false;
    }


    public List<History> getCurrencyData(String currency) {
        List<History> currencyData = new ArrayList<History>();
        String query = "SELECT balance, updatedat FROM History WHERE currency = " + "'" + currency + "'";
        try {
            this.query("result", query);
            result = this.result();
            while (result.next()) {
                History singleCurrency = new History(
                        currency,
                        result.getDouble("balance"),
                        result.getString("updatedat")
                );
                currencyData.add(singleCurrency);
            }

        } catch (SQLException e) {
            System.out.println("SQL error" + e);
        }
        return currencyData;
    }

    public List<History> getAllCurrencies() {
        List<History> currencyData = new ArrayList<History>();
        String query = "SELECT currency FROM PortofolioCoin WHERE balance != 0";
        try {
            this.query("result", query);
            result = this.result();
            while (result.next()) {
                History singleCurrency = new History(
                        result.getString("currency")
                );
                currencyData.add(singleCurrency);
            }

        } catch (SQLException e) {
            System.out.println("SQL error" + e);
        }
        return currencyData;
    }

    public String setDate() {
        DateFormat format = new SimpleDateFormat("dd-MM-YYYY");
        Date newDate = new Date();
        return format.format(newDate);
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
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

    public Double getPending() {
        return pending;
    }

    public void setPending(Double pending) {
        this.pending = pending;
    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }
}
