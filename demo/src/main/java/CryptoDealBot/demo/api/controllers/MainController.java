package CryptoDealBot.demo.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static CryptoDealBot.demo.business.RequestSender.sendNextRequest;
import static CryptoDealBot.demo.business.ResponseJsonParser.getMarkPrice;

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
}
