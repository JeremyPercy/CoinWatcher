package bittrexApi;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class containing methods to extract information from Bittrex via ApiInterface
 */
public class Public extends BittrexApi {

    /**
     * Constructor Bittrex ApiInterface
     *
     * @param secretKey
     * @param publicKey
     */
    public Public(String secretKey, String publicKey) {
        super(secretKey, publicKey);
    }

    /**
     * Get the open and available trading markets at Bittrex along with other meta data.
     * returns Json array with open markets
     *
     * @return JSONArray
     */
    public JSONArray getMarkets() {
        JSONObject json = this.get("/public/getmarkets?");
        if (!json.isEmpty()) {
            return json.getJSONArray("result");
        }
        return new JSONArray("[]");
    }

    /**
     * Returns json array with all available currencies
     *
     * @return JSONArray
     */
    public JSONArray getCurrencies() {
        JSONObject json = this.get("/public/getcurrencies?");

        if (json.getString("success").contentEquals("true")) {
            return json.getJSONArray("result");
        }
        return new JSONArray("[]");
    }

    /**
     * Returns json array with information about ticker
     * Example: WalletController.MarketCoin = BTC-LTC
     * Returns  "Bid" : 2.05670368,
     * "Ask" : 3.35579531,
     * "Last" : 3.35579531
     *
     * @param market example: BTC-LTC
     * @return JSON array
     */
    public JSONObject getTicker(String market) {
        JSONObject json = this.get("/public/getticker?market=" + market + "&");
        if (json.isEmpty()) {
            return new JSONObject("{}");
        }
        return (JSONObject) json.get("result");
    }


}