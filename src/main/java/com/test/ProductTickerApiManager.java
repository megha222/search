package com.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/// This class reads the product ticker input list from coinbase api
public class ProductTickerApiManager {
    private final static String BaseUrl = "https://api.pro.coinbase.com";
    private final ObjectMapper mapper = new ObjectMapper();
    public ProductTickerApiManager() {

    }

    public List<CurrencyExchangeInput> fetchProductList() {
        List<CurrencyExchangeInput> products = new ArrayList<>();
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpGet httpget = new HttpGet(BaseUrl);
                HttpResponse response = httpClient.execute(httpget);
                String responseStr = response.getEntity().toString();
                JsonNode node = mapper.readTree(responseStr);
                ArrayNode arrayNode = (ArrayNode) node;
                for (int i = 0; i < arrayNode.size(); i++) {
                    JsonNode arrayElement = arrayNode.get(i);
                    String productId = arrayElement.get("id").textValue();
                    //CurrencyExchangeInput input = getProductTicker(productId);
                    //products.add(input);

                }
            } catch (IOException e) {
                //Logger.log("Error reading input list", e);
            }
        return products;
    }

    /*private CurrencyExchangeInput getProductTicker(String id) {

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet(SBaseUrl);
            HttpResponse response = httpClient.execute(httpget);
            String responseStr = response.getEntity().toString();
            JsonNode node = mapper.readTree(responseStr);
            String productId = node.get("id").textValue();

            CurrencyExchangeInput input = getProductTicker(productId);

            }
        } catch (IOException e) {
            //Logger.log("Error reading input list", e);
        }
    }*/
}
