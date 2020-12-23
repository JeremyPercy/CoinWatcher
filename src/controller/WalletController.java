package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.History;
import model.PortfolioCoin;
import model.Ticker;
import util.AlertBox;
import util.Charts;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WalletController implements Initializable {
    @FXML
    private TableView<PortfolioCoin> coinTable;
    @FXML
    private TableColumn<PortfolioCoin, String> exchange, balance, currency, available, pending, createdat, updatedat;
    @FXML
    private TableColumn<PortfolioCoin, String> valueBTC, valueEUR;
    @FXML
    private Label welcomeUser;

    private PortfolioCoin portfolioCoins;
    private UserController user;
    private ImportController importer;
    private History history;

    public WalletController() {
        this.portfolioCoins = new PortfolioCoin();
        this.user = new UserController();
        this.importer = new ImportController();
    }

    private List<PortfolioCoin> getCoinList() {
        return this.portfolioCoins.getAllCoins();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IndexController importer = new IndexController();
        try {
            importer.importCoins();
            welcomeUser.setText("Overview Crypto coins from user: " + this.user.whoIs());
            saveToHistory();
        } catch (SQLException e) {
            AlertBox.display("Something went wrong by displaying your name");
        } catch (Exception e) {
            AlertBox.display("Something went wrong with importing the coins, check your API key");
            System.out.println(e.getMessage());
        }

        exchange.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("Exchange"));
        balance.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("Balance"));
        currency.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("Currency"));
        available.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("Available"));
        pending.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("Pending"));
        updatedat.setCellValueFactory(new PropertyValueFactory<PortfolioCoin, String>("UpdatedAt"));
        coinTable.getItems().setAll(getCoinList());

        valueBTC.setCellFactory(column -> new TableCell<PortfolioCoin, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                PortfolioCoin coin = this.getTableView().getItems().get(getIndex());
                setText("");
                if (!empty)
                    setText(calculateValueInBTC(coin.getCurrency(), coin.getBalance()));
            }
        });
    }

    public void saveToHistory() {
        for (PortfolioCoin coin : getCoinList()) {
            history = new History(
                    coin.getExchange(),
                    coin.getBalance(),
                    coin.getCurrency(),
                    coin.getAvailable(),
                    coin.getPending()
            );
            history.create();
        }
    }

    public void loadChart() {
        PortfolioCoin setCurrency = coinTable.getSelectionModel().getSelectedItem();
        Charts.display(setCurrency.getCurrency());
    }

    /**
     * Function to calculate Value in BTC by currency and balance
     *
     * @param currency
     * @param balance
     * @return String
     */
    private String calculateValueInBTC(String currency, Double balance) {
        if (currency.contentEquals("BTC")) {
            return balance.toString();
        }
        Ticker ticker = new Ticker();
        ticker.setMarket("BTC-" + currency);
        ticker.readwithBool();
        Double btc = ticker.getAsk();
        Double value = btc * balance;
        NumberFormat formatter = new DecimalFormat("##.##########");

        return formatter.format(value);
    }
}
