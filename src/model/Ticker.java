package model;

import java.sql.SQLException;

public class Ticker extends Model {

    private final String TB_NAME = "ticker";
    private String market;
    private double bid, ask, last;

    @Override
    public boolean createDbTable() {
        return this.query("update",
                "CREATE TABLE IF NOT EXISTS " + TB_NAME +
                        " ('market' string, 'bid' double, 'ask' double, 'last' double)");
    }

    @Override
    public boolean create() {
        return this.query("update", "INSERT INTO " + TB_NAME +
                " ('market', 'bid', 'ask', 'last') " +
                "VALUES('" +
                getMarket() + "', '" +
                getBid() + "', '" +
                getAsk() + "', '" +
                getLast() + "')");
    }

    @Override
    public void createOrUpdate() throws Exception {
        if(readwithBool()) {
            update();
        } else {
            create();
        }
    }

    @Override
    public void read() {
        return;
    }

    /**
     * Same function as read, with a return of boolean when a record has been found
     * @return
     */
    public boolean readwithBool() {
        String query = "select * from " + TB_NAME;
        if(!this.market.isEmpty()) {
            query += " where `market` = '" + this.market + "'";
        }

        this.query("result", query);
        try {
            if(!result().next()) {
                return false;
            }
            setAsk(result().getDouble("ask"));
            setBid(result().getDouble("bid"));
            setLast(result().getDouble("last"));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update() throws Exception {
        if(getMarket().isEmpty()){
            throw new Exception("Market cannot be empty when updating a ticker");
        }

        String query =
                "UPDATE  " + TB_NAME + " SET " +
                        "`ask` = '" + getAsk() + "'," +
                        "`bid` = '" + getBid() + "'," +
                        "`last` = '" + getLast() + "'";
        return this.query("update", query);
    }

    @Override
    public boolean delete() throws Exception {
        return false;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "market='" + market + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", last=" + last +
                '}';
    }
}
