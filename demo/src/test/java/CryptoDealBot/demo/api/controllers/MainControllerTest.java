package CryptoDealBot.demo.api.controllers;

import CryptoDealBot.demo.business.exceptions.TemporaryDataUnavailableException;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    @Test
    public void checkingForEmpty() {
        String URL = "https://testnet.binancefuture.com/fapi/v1/premiumIndex?symbol=BTCUSDT";
        System.out.println(sendNextRequest(URL));
    }

    private static String sendNextRequest (String URL) {
        String response = new RestTemplate().getForEntity(URL+"3456", String.class).getBody();;
        //assert response != null;
        if (response.isEmpty()) {
            System.out.println("Throws Exception - Throws Exception - Throws Exception - Throws Exception - Throws Exception - Throws Exception - Throws Exception - ");
            throw new TemporaryDataUnavailableException("Data is not available yet. Please try again.");
        }
        return response;
    }
}