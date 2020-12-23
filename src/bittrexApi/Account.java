package bittrexApi;

import org.json.JSONArray;
import org.json.JSONObject;

public class Account extends BittrexApi {


    /**
     * Constructor Bittrex ApiInterface
     *
     * @param secretKey
     * @param publicKey
     */
    public Account(String secretKey, String publicKey) {
        super(secretKey, publicKey);
    }

    /**
     * Used to retrieve all balances from your account.
     * @return JSONarray
     */
    public JSONArray getBalances () {

        JSONObject json = this.get("/account/getbalances?");

        if(!json.isEmpty() && !json.getJSONArray("result").isEmpty()) {
            return json.getJSONArray("result");
        }

        return new JSONArray("[]");
    }
}
