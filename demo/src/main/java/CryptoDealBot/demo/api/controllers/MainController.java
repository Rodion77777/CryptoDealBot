package CryptoDealBot.demo.api.controllers;

import CryptoDealBot.demo.business.exceptions.TemporaryDataUnavailableException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Tag(name = "CryptoPair controller")
@RestController
@RequestMapping("/main")
public class MainController {

    @Operation(
            summary = "Returns cryptopair data.",
            description = "Requests data on your crypto pair and returns it.",
            parameters = {
                @Parameter(
                        name = "nameCryptoPair",
                        description = "Name of your crypto pair.",
                        required = true
                )
            }
    )
    @GetMapping("/getNextCryptoPair")
    public String getNextCryptoPair(@RequestParam String nameCryptoPair) {
        String URL = "https://testnet.binancefuture.com/fapi/v1/premiumIndex?symbol=" + nameCryptoPair;
        return sendNextRequest(URL);
    }

    @Operation(
            summary = "Returns \"markPrice\" value",
            description = "Requests data on your crypto pair, and returns only the \"markPrice\" value.",
            parameters = {
                    @Parameter(
                            name = "nameCryptoPair",
                            description = "Name of your crypto pair.",
                            required = true
                    )
            }
    )
    @GetMapping("/getNextCryptoPairMarkPrice")
    public String getNextCryptoPairMarkPrice(@RequestParam String nameCryptoPair) {
        try {
            String jsonResponse = getNextCryptoPair(nameCryptoPair);
            double markPrice = getMarkPrice(jsonResponse);
            return String.valueOf(markPrice);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Retryable(value = {TemporaryDataUnavailableException.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    private static String sendNextRequest(String URL) {
        try {
            String response = new RestTemplate().getForEntity(URL, String.class).getBody();
            if (response == null || response.isEmpty())
                throw new TemporaryDataUnavailableException("Data is not available yet. Please try again.");
            return response;
        } catch (HttpClientErrorException | ResourceAccessException e) {
            return e.getMessage();
        }
    }

    private static double getMarkPrice(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        return rootNode.get("markPrice").asDouble();
    }
}
