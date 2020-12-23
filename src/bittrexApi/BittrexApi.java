package bittrexApi;

import interfaces.ApiInterface;
import util.AlertBox;
import util.Encryption;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

abstract class BittrexApi implements ApiInterface {

    private final String NAME = "Bittrex";

    /**
     * Base url to connect with the ApiInterface bittrex
     */
    private final String BASE_URL = "https://bittrex.com/api/";

    /**
     * Bittrex ApiInterface VERSION
     */
    private final String VERSION = "v1.1";

    /**
     * Secrect key
     */
    private String secretKey;

    /**
     * Public key
     */
    private String publicKey;

    /**
     * Httpconnection
     */
    private HttpURLConnection connection;

    /**
     * Constructor Bittrex ApiInterface
     */
    public BittrexApi(String secretKey, String publicKey) {
        this.secretKey = secretKey;
        this.publicKey = publicKey;
    }

    /**
     * Public function to get requested information from exchange
     */
    public JSONObject get(String endpoint) {
        try {
            String buildUrl = BASE_URL + VERSION + endpoint + "apikey=" + this.publicKey + "&nonce="  + getNonce();
            //Build url and open connection
            URL url = new URL(buildUrl);
            buildConnection(url);
            JSONObject content = getContent();
            if(!content.get("success").toString().contentEquals("true"))
                throw new IOException(content.get("message").toString());
            return content;
        } catch (IOException e) {
            System.out.println("Something went wrong with connecting to Bittrex: " + e.getMessage());
            //return empty JSONObject when connection failed
            return new JSONObject();
        }
    }

    /**
     * Gets content from Bittrex
     * @return content as JSON Object
     * Empty JSONObject when connection failed
     * @throws IOException
     */
    public JSONObject getContent() throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        String jsonOutput = "";
        while ((inputLine = in.readLine()) != null) {
            jsonOutput = content.append(inputLine).toString();
        }
        in.close();
        return new JSONObject(jsonOutput);
    }

    /**
     * Builds connection via HTTP request
     * @param url needs full URL such as 'https://bittrex.com/api/v1.1/market/getopenorders'
     * @throws IOException
     */
    public void buildConnection(URL url) throws IOException {
        this.connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("apisign", Encryption.calculateHash( this.secretKey, url.toString()));
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.flush();
        out.close();
        connection.disconnect();
    }

    /**
     * calculates Nonce to use as param with Bittrex
     * @return nonce
     */
    private static Long getNonce () {
        return System.currentTimeMillis() / 1000;
    }

    public String getName() {
        return NAME;
    }
}
