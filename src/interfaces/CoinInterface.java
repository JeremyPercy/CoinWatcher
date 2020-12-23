package interfaces;

public interface CoinInterface {

    /**
     * Gets currency from Coin.
     *
     * @return String Example: BTC
     */
    String getCurrency();

    /**
     * Sets currency
     *
     * @param currency
     */
    void setCurrency(String currency);
}
