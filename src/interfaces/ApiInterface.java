package interfaces;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface ApiInterface {

    String getName();

    /**
     * Public function to get requested information from exchange
     * Uses buildConnection function to build connection and getContent to return content
     * @param endpoint Example: '/account/getBalances'
     * @return Json Object with data
     */
    JSONObject get(String endpoint);

    /**
     * Builds connection with ApiInterface from exchange
     * @param url needs full URL such as 'https://bittrex.com/api/v1.1/market/getopenorders'
     * @throws IOException
     */
    void buildConnection(URL url) throws IOException;

    /**
     * Gets content from exchange and return it as JSONObject
     * @return content as JSONobject
     * @throws IOException
     */
    JSONObject getContent() throws IOException;
}